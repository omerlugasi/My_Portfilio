import fs from 'fs/promises';
import path from 'path';
import config from '../Config/getConfig.js';
import { matchRule } from '../Helpers/ruleMatcher.js';
import { applyAction } from '../Helpers/fileActions.js';
import { logger } from '../Logger/logger.js';

export async function runFolderJob({
  folderPath,
  rules,
  summary,
  skipEntry,
  onRuleMatched,
  onSuccess,
  onRates
}) {
  const entries = await fs.readdir(folderPath);

  for (const entry of entries) {
    if (skipEntry?.(entry)) continue;

    const filePath = path.join(folderPath, entry);
    const stats = await fs.stat(filePath);

    summary.checked++;

    if (!stats.isFile()) continue;

    const rule = matchRule(entry, stats, rules);

    if (!rule) continue;

    onRuleMatched?.(summary);

    try {
      await applyAction(rule, filePath, config);

      logger.debug(rule.successLogMessage || 'File processed successfully', {
        fileName: entry,
        ruleName: rule.name,
        action: rule.action
      });

      onSuccess?.(summary, rule, entry);

    } catch (err) {
      summary.failed++;

      summary.failures.push({
        fileName: entry,
        action: rule.action,
        reason: err.message
      });

      logger.error(rule.errorLogMessage || 'Failed processing file', {
        fileName: entry,
        ruleName: rule.name,
        action: rule.action,
        error: err.message
      });
    }
  }

  onRates?.(summary);

  return summary;
}