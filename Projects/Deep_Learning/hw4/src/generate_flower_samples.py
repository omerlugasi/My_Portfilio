# hw4_generation.py
import os
import pickle
import torch
from torchvision.utils import save_image
import torch.nn as nn


class DCGANGenerator(nn.Module):
    def __init__(self, latent_dim: int = 128, base_channels: int = 64, image_channels: int = 3):
        super().__init__()
        self.net = nn.Sequential(
            nn.ConvTranspose2d(latent_dim, base_channels*8, 4, 1, 0, bias=False),
            nn.BatchNorm2d(base_channels*8),
            nn.ReLU(True),

            nn.ConvTranspose2d(base_channels*8, base_channels*4, 4, 2, 1, bias=False),
            nn.BatchNorm2d(base_channels*4),
            nn.ReLU(True),

            nn.ConvTranspose2d(base_channels*4, base_channels*2, 4, 2, 1, bias=False),
            nn.BatchNorm2d(base_channels*2),
            nn.ReLU(True),

            nn.ConvTranspose2d(base_channels*2, base_channels, 4, 2, 1, bias=False),
            nn.BatchNorm2d(base_channels),
            nn.ReLU(True),

            nn.ConvTranspose2d(base_channels, image_channels, 4, 2, 1, bias=False),
            nn.Tanh()
        )

    def forward(self, z):
        return self.net(z)

def _select_diverse_indices(z_flat: torch.Tensor, k: int = 10) -> list:
    """
    Greedy selection for diversity in latent space:
    pick 1 random, then repeatedly pick the point farthest from the chosen set.
    z_flat: (N, latent_dim)
    returns: list of indices length k
    """
    N = z_flat.size(0)
    # normalize for fair cosine-ish comparison (optional)
    z = torch.nn.functional.normalize(z_flat, dim=1)

    # start with the vector with largest norm before normalization is same, so just pick 0
    chosen = [0]

    # precompute pairwise distances efficiently (greedy)
    # dist(i, S) = min_j ||z_i - z_j||^2
    dmin = torch.full((N,), float("inf"), device=z.device)
    for _ in range(1, k):
        last = z[chosen[-1]].unsqueeze(0)               # (1, d)
        dist2 = ((z - last) ** 2).sum(dim=1)            # (N,)
        dmin = torch.minimum(dmin, dist2)
        next_idx = torch.argmax(dmin).item()
        chosen.append(next_idx)

    return chosen


def reproduce_hw4(
    model_path: str = "hw4_model.pkl",
    out_dir: str = "reproduce_outputs",
    n_images: int = 10,
    pool_size: int = 80,   # larger pool => more diversity
    seed: int = 42,
):
    """
    Loads the trained GAN generator and generates 10 diverse images (not class-specific).
    Saves images to out_dir/img_00.png ... img_09.png
    """
    device = torch.device("cuda" if torch.cuda.is_available() else "cpu")

    # reproducibility
    torch.manual_seed(seed)
    if device.type == "cuda":
        torch.cuda.manual_seed_all(seed)

    if not os.path.exists(model_path):
        raise FileNotFoundError(f"Missing {model_path}. Put it next to this file.")

    with open(model_path, "rb") as f:
        ckpt = pickle.load(f)

    latent_dim = int(ckpt.get("LATENT_DIM", 128))

    # build + load generator
    G = DCGANGenerator(latent_dim=latent_dim).to(device)
    G.load_state_dict(ckpt["G_state_dict"])
    G.eval()

    os.makedirs(out_dir, exist_ok=True)

    with torch.no_grad():
        # 1) sample a pool
        z = torch.randn(pool_size, latent_dim, 1, 1, device=device)
        fake = G(z)  # expected in [-1,1] if you used Tanh

        # 2) pick diverse subset based on z
        z_flat = z.view(pool_size, latent_dim)
        idxs = _select_diverse_indices(z_flat, k=n_images)

        # 3) save selected images
        for i, idx in enumerate(idxs):
            path = os.path.join(out_dir, f"img_{i:02d}.png")
            save_image(fake[idx], path, normalize=True, value_range=(-1, 1))

    print(f"Saved {n_images} images to: {out_dir}")


if __name__ == "__main__":
    reproduce_hw4()
