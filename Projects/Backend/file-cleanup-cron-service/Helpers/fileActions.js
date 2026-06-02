import fsp from 'node:fs/promises';
import path from 'path';

export async function applyAction(rule, filePath, config) {
  if (rule.action === 'delete') {
    try {
      await fsp.rm(filePath);
      return;
    } catch (err) {
      throw new Error(`Delete failed: ${err.message}`);
    }
  }

  if (rule.action === 'move') {
    const fileName = path.basename(filePath);
    const targetFolder = config.folders[rule.targetFolderKey];
    const destPath = path.join(targetFolder, fileName);

    try {
      await fsp.cp(filePath, destPath);
    } catch (err) {
      throw new Error(`Copy failed: ${err.message}`);
    }

    try {
      await fsp.rm(filePath);
    } catch (err) {
      throw new Error(`Source delete after copy failed: ${err.message}`);
    }

    return;
  }

  throw new Error(`Unsupported action: ${rule.action}`);
}