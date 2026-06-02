"""## Part 4: Contrastive Learning (40pt)

In this section, we will work on creating informative embeddings for images using SimCLR. For this section we will use the subset of the popular ImageNet dataset of 96x96 images from 1000 classes. Below, we provide you with several functions to implement a contrastive learning model.

The data is already downloaded to your VMs in the zip tiny-imagenet-200. You can also find it at https://www.kaggle.com/datasets/nikhilshingadiya/tinyimagenet200/data. Note that training time in this section can be long, $\approx 5$ minutes for epoch.

### Dry Questions

In this section, we will work on creating informative embeddings for images using SimCLR. For this section we will use the subset of the popular ImageNet dataset of 96x96 images from 1000 classes. Below, we provide you with several functions to implement a contrastive learning model.

The data is already downloaded to your VMs in the zip tiny-imagenet-200. You can also find it at https://www.kaggle.com/datasets/nikhilshingadiya/tinyimagenet200/data. Note that training time in this section can be long, $\approx 5$ minutes for epoch.

Before implementation, take these questions in consideration (and provide your answers and explanations):

1. When training an unsupervised contrastive learning model such as SimCLR, would we prefer to have a large or small batch size?
2. When creating embeddings for images in the test set, how does the process differ from what we do in training?
3. For each of the following image augmentations, explain whether or not we would like to use them in the SimCLR framework:
    - Randomly cropping a fixed-size window in the image.
    - Enlarging the image to 128x128.
    - Random rotation of the image.
    - Adding Gaussian noise.
    - Randomly changing the image's dimensions.
    - Randomly converting the image to grayscale.

### Code

Do the following:

1. Create a CNN that makes embeddings for images (you can use pretrained foundation models if you'd like, as long as they **were not** trained on ImageNet).
2. Implement an unsupervised contrastive loss (such as nt-xent in SimCLR).
3. Train the model (achieve loss less than 3.0). Use only the train set.

Note you are graded not only on the loss but also on the quality of the embeddings. If the augmentations aren't diverse enough, the loss may be low but similar images won't learn to be mapped to similar embeddings. You must use at least 3 random augmentations in the SimCLR training (the more, the better).
"""

!unzip -q archive.zip -d .
!ls

from PIL import Image
import os
import matplotlib.pyplot as plt
from matplotlib.offsetbox import OffsetImage, AnnotationBbox  # For visualization
from sklearn.manifold import TSNE  # Dimensionality Reduction


TRAIN_PATH = "/content/tiny-imagenet-200/train"
TEST_PATH  = "/content/tiny-imagenet-200/test"
BATCH_SIZE = 256

import torch
import torchvision
from torch.utils.data import DataLoader
from torchvision import transforms

class TrainDataset(torch.utils.data.Dataset):
    def __init__(self, root, transform=None):
        """
        Initialize the dataset with the root directory and the transform to be applied.
        """
        self.root = root
        self.transform = transform
        self.imgs = []
        for label in os.listdir(root):
            if os.path.isdir(os.path.join(root, label, 'images')):
                for img in os.listdir(os.path.join(root, label, 'images')):
                    self.imgs.append(os.path.join(root, label, 'images', img))

    def __len__(self):
        """
        Return the length of the dataset.
        """
        return len(self.imgs)

    def __getitem__(self, idx):
        """
        Fetch the image and apply two different augmentations to it.
        """
        img_path = self.imgs[idx]
        img = Image.open(img_path).convert('RGB')  # Ensure it's in RGB format

        if self.transform:
            img1 = self.transform(img)  # First random augmentation
            img2 = self.transform(img)  # Second random augmentation
        else:
            img1, img2 = img, img

        return img1, img2  # Return both augmented versions


class TestDataset(torch.utils.data.Dataset):
    def __init__(self, root, transform=None):
        self.root = root
        self.transform = transform
        self.imgs = []
        for label in os.listdir(root):
            for img in os.listdir(os.path.join(root, label)):
                self.imgs.append(os.path.join(root, label, img))

    def __len__(self):
        return len(self.imgs)

    def __getitem__(self, idx):
        img_path = self.imgs[idx]
        img = Image.open(img_path).convert("RGB")
        if self.transform:
            img_transform = self.transform(img)
        return transforms.ToTensor()(img), img_transform  # Return the original image (for visualization) and the image that will be used in the model


data_transforms = transforms.Compose([
    transforms.RandomResizedCrop(96, scale=(0.2, 1.0)),
    transforms.RandomHorizontalFlip(p=0.5),
    transforms.RandomApply(
        [transforms.ColorJitter(
            brightness=0.4,
            contrast=0.4,
            saturation=0.4,
            hue=0.1
        )],
        p=0.8
    ),
    transforms.RandomGrayscale(p=0.2),
    transforms.ToTensor(),
])


test_transforms = transforms.Compose([
    transforms.Resize((96, 96)),
    transforms.ToTensor(),
])

train_data = TrainDataset(TRAIN_PATH, transform=data_transforms)
train_loader = DataLoader(train_data, batch_size=BATCH_SIZE, shuffle=True, drop_last=True)

test_data = TestDataset(TEST_PATH, transform=test_transforms)
test_loader = DataLoader(test_data, batch_size=BATCH_SIZE)

import torch.nn as nn
from torchvision import models


class SimCLR_Encoder(nn.Module):
    def __init__(self, embedding_dim=64):
        super(SimCLR_Encoder, self).__init__()
        resnet = models.resnet18(weights=None)
        self.encoder = nn.Sequential(*list(resnet.children())[:-1])
        self.projection_head = nn.Sequential(
            nn.Linear(resnet.fc.in_features, 128),  # 512 -> 128
            nn.ReLU(),                              # Non-linear activation
            nn.Linear(128, embedding_dim)           # 128 -> embedding_dim (e.g., 64)
        )

    def forward(self, x):
        # 1. Extract feature maps from the backbone
        features = self.encoder(x)

        # 2. Flatten the feature maps to a 1D vector
        features = features.view(features.size(0), -1)

        # 3. Project to the embedding space (used for contrastive loss)
        embeddings = self.projection_head(features)

        return embeddings

import torch.nn.functional as F

def nt_xent_loss(embeddings1, embeddings2, temperature=0.5):
    # L2 normalization is crucial for cosine similarity
    embeddings1 = F.normalize(embeddings1, dim=1)
    embeddings2 = F.normalize(embeddings2, dim=1)

    batch_size = embeddings1.size(0)
    device = embeddings1.device

    # Concatenate embeddings from both views
    embeddings = torch.cat([embeddings1, embeddings2], dim=0)

    # Calculate similarity matrix
    similarity_matrix = torch.matmul(embeddings, embeddings.T) / temperature

    # Create a mask to remove self-similarity (the diagonal)
    mask = torch.eye(2 * batch_size, dtype=torch.bool).to(device)
    similarity_matrix = similarity_matrix.masked_fill(mask, -1e4)

    # Define labels:
    # First half should match the second half and vice versa
    labels = torch.cat([
        torch.arange(batch_size, 2 * batch_size),
        torch.arange(0, batch_size)
    ]).to(device)

    # Cross-entropy loss
    loss_fn = nn.CrossEntropyLoss()
    loss = loss_fn(similarity_matrix, labels)

    return loss

from tqdm import tqdm
import torch
import torch.optim as optim
from torch.cuda.amp import GradScaler, autocast

device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')

model = SimCLR_Encoder().to(device)
optimizer = optim.Adam(model.parameters(), lr=0.0003)
scaler = GradScaler()

num_epochs = 7
accumulation_steps = 4

for epoch in range(num_epochs):
    model.train()
    total_loss = 0.0
    optimizer.zero_grad()

    with tqdm(train_loader, unit="batch") as tepoch:
        tepoch.set_description(f"Epoch [{epoch+1}/{num_epochs}]")

        for i, (img1, img2) in enumerate(tepoch):
            img1 = img1.to(device, non_blocking=True)
            img2 = img2.to(device, non_blocking=True)

            with autocast():
                embeddings1 = model(img1)
                embeddings2 = model(img2)

            # compute contrastive loss in fp32 (outside autocast)
            loss = nt_xent_loss(embeddings1.float(), embeddings2.float(), temperature=0.2) / accumulation_steps

            scaler.scale(loss).backward()

            if (i + 1) % accumulation_steps == 0 or (i + 1) == len(train_loader):
                scaler.step(optimizer)
                scaler.update()
                optimizer.zero_grad()

            total_loss += loss.item() * accumulation_steps
            tepoch.set_postfix(loss=(loss.item() * accumulation_steps))

    avg_loss = total_loss / len(train_loader)
    print(f"Epoch [{epoch+1}/{num_epochs}] Avg Loss: {avg_loss:.4f}")

torch.cuda.empty_cache()

"""Using the function ```plot_embeddings```, show the model's performance on the test data."""

def plot_embeddings(model, test_loader, device):
    """
    This function creates embeddings for each image in the first batch of the test loader, projects them to 2D and displays them on a plot.
    """
    for img, img_transform in test_loader:
        img_transform = img_transform.to(device)
        embeddings = model(img_transform)
        tsne = TSNE(n_components=2)
        embeddings = tsne.fit_transform(embeddings.cpu().detach().numpy())

        fig, ax = plt.subplots()
        ax.set_xlim(-5, 5)
        ax.set_ylim(-5, 5)
        for i in range(embeddings.shape[0]):
            curr_img = img[i].permute(1,2,0)
            curr_img = OffsetImage(curr_img, zoom=0.5)
            ab = AnnotationBbox(curr_img, embeddings[i], frameon=False)
            ax.add_artist(ab)
        plt.show()
        return

plot_embeddings(model, test_loader,device)

"""### Evaluation
1. Choose two different classes from the train set and plot the projected embeddings of 20 images from each class. Is the model able to separate?
2. For some batch of the test loader, take 3 images in the batch. For each image, find and display the 5 images within the batch that have the closest embeddings to them (using mse). Do the chosen images make sense? What features do the images have in common? If not, what could have possibly gone wrong with your model?

**Q1**
"""

import os
import random
import numpy as np
import matplotlib.pyplot as plt
from PIL import Image
from sklearn.manifold import TSNE
import torch

def sample_20_images_per_class(train_root, class_name, n=20, seed=0):
    random.seed(seed)
    class_img_dir = os.path.join(train_root, class_name, "images")
    imgs = [os.path.join(class_img_dir, f) for f in os.listdir(class_img_dir)]
    imgs = [p for p in imgs if os.path.isfile(p)]
    return random.sample(imgs, n)

def get_projected_embeddings(paths, model, device, transform):
    model.eval()
    xs = []
    for p in paths:
        img = Image.open(p).convert("RGB")
        xs.append(transform(img))  # deterministic preprocessing
    x = torch.stack(xs).to(device)

    with torch.no_grad():
        emb = model(x)  # projected embeddings (projection head output)
    return emb.float().cpu().numpy()

def plot_two_classes_projected_embeddings(TRAIN_PATH, class_a, class_b, model, device, transform, n=20, seed=0):
    # 1) sample images
    paths_a = sample_20_images_per_class(TRAIN_PATH, class_a, n=n, seed=seed)
    paths_b = sample_20_images_per_class(TRAIN_PATH, class_b, n=n, seed=seed+1)

    # 2) projected embeddings
    emb_a = get_projected_embeddings(paths_a, model, device, transform)
    emb_b = get_projected_embeddings(paths_b, model, device, transform)

    # 3) concatenate + t-SNE to 2D
    X = np.vstack([emb_a, emb_b])
    y = np.array([0]*n + [1]*n)

    tsne = TSNE(n_components=2, perplexity=10, init="random", random_state=seed)
    X2 = tsne.fit_transform(X)

    # 4) plot
    plt.figure(figsize=(7,6))
    plt.scatter(X2[y==0, 0], X2[y==0, 1], label=class_a, alpha=0.8)
    plt.scatter(X2[y==1, 0], X2[y==1, 1], label=class_b, alpha=0.8)
    plt.title(f"t-SNE of projected embeddings (20 images each)\n{class_a} vs {class_b}")
    plt.legend()
    plt.grid(True)
    plt.show()

# choose 2 different classes from the train set
# easiest: pick from the folder names inside TRAIN_PATH
all_classes = [d for d in os.listdir(TRAIN_PATH) if os.path.isdir(os.path.join(TRAIN_PATH, d, "images"))]
print("Number of classes found:", len(all_classes))
print("Example classes:", all_classes[:10])

# choose two classes
class_a = all_classes[0]
class_b = all_classes[1]

plot_two_classes_projected_embeddings(
    TRAIN_PATH=TRAIN_PATH,
    class_a=class_a,
    class_b=class_b,
    model=model,
    device=device,
    transform=test_transforms,  # use deterministic transforms for evaluation
    n=20,
    seed=0
)

"""**Q2**"""

import torch
import numpy as np
import matplotlib.pyplot as plt

def denorm_img(x):
    # x: CHW tensor in [0,1] already (because it's transforms.ToTensor() without Normalize)
    x = x.permute(1, 2, 0).cpu().numpy()
    return np.clip(x, 0, 1)

def find_closest_images_mse(model, test_loader, device, num_queries=3, num_closest=5, seed=0):
    model.eval()
    torch.manual_seed(seed)

    with torch.no_grad():
        # take one batch
        img_batch, img_trans_batch = next(iter(test_loader))
        img_trans_batch = img_trans_batch.to(device)

        # embeddings for whole batch (projected embeddings)
        emb = model(img_trans_batch).float()   # shape: [B, D]
        # embeddings for whole batch (use encoder features instead of projection head)
        # feats = model.encoder(img_trans_batch)              # [B, 512, 1, 1]
        # emb = feats.view(feats.size(0), -1).float()         # [B, 512]
        emb = torch.nn.functional.normalize(emb, dim=1)


        B = emb.size(0)

        # choose 3 query indices
        query_indices = torch.randperm(B)[:num_queries]

        for qi in query_indices:
            qi = qi.item()

            # compute MSE distance to all embeddings in batch: mean((e_i - e_q)^2)
            diffs = emb - emb[qi].unsqueeze(0)            # [B, D]
            mse_dist = (diffs ** 2).mean(dim=1)           # [B]

            # exclude the query itself by setting its distance to +inf
            mse_dist[qi] = float("inf")

            # take 5 smallest distances
            closest_idx = torch.topk(mse_dist, k=num_closest, largest=False).indices.cpu().numpy()

            # plot: query + 5 closest
            fig, axes = plt.subplots(1, num_closest + 1, figsize=(3*(num_closest+1), 3))

            axes[0].imshow(denorm_img(img_batch[qi]))
            axes[0].set_title("Query")
            axes[0].axis("off")

            for j, idx in enumerate(closest_idx):
                axes[j+1].imshow(denorm_img(img_batch[idx]))
                axes[j+1].set_title(f"Closest {j+1}")
                axes[j+1].axis("off")

            plt.tight_layout()
            plt.show()


find_closest_images_mse(model, test_loader, device, num_queries=3, num_closest=5, seed=0)