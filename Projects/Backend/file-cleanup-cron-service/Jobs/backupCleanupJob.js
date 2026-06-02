import config from '../Config/getConfig.js';
import { runFolderJob } from './folderJob.js';

export async function runBackupCleanupJob() {
  const summary = {
    checked: 0,
    eligibleForDelete: 0,
    deleted: 0,
    failed: 0,
    failures: [],
    successRate: 0,
    failureRate: 0
  };

  return runFolderJob({
    folderPath: config.folders.backup,
    rules: config.backupRules,
    summary,

    onRuleMatched: (summary) => {
      summary.eligibleForDelete++;
    },

    onSuccess: (summary) => {
      summary.deleted++;
    },

    onRates: (summary) => {
      summary.successRate =
        (summary.deleted)
          ? Number(((summary.deleted / summary.eligibleForDelete) * 100).toFixed(1))
          : 0;
      summary.failureRate = 
        (summary.failed)
          ? Number(((summary.failed / summary.eligibleForDelete) * 100).toFixed(1))
          : 0;
    }
  });
}