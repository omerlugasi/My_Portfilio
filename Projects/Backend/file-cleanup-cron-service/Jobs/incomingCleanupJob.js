import path from 'path';
import config from '../Config/getConfig.js';
import { runFolderJob } from './folderJob.js';

export async function runIncomingCleanupJob() {
  const summary = {
    checked: 0,
    matchedFiles: 0,
    movedToBacklog: 0,
    deleted: 0,
    failed: 0,
    failures: [],
    movedBlocked: 0,
    movedNotBlocked: 0,
    successRate: 0,
    failureRate: 0 
  };

  return runFolderJob({
    folderPath: config.folders.main,
    rules: config.incomingRules,
    summary,

    skipEntry: (entry) => entry === path.basename(config.folders.backup),

    onRuleMatched: (summary) => {
      summary.matchedFiles++;
    },

    onSuccess: (summary, rule, entry) => {
      if (rule.action === 'move') {
        summary.movedToBacklog++;

        if (entry.toLowerCase().includes('blocked')) {
          summary.movedBlocked++;
        } else {
          summary.movedNotBlocked++;
        }
      }

      if (rule.action === 'delete') {
        summary.deleted++;
      }
    },

    onRates: (summary) => {
      summary.successRate =
        (summary.deleted + summary.movedToBacklog)
          ? Number((((summary.deleted + summary.movedToBacklog) / summary.matchedFiles) * 100).toFixed(1))
          : 0;
      summary.failureRate = 
        (summary.failed)
          ? Number(((summary.failed / summary.matchedFiles) * 100).toFixed(1))
          : 0;
    }
  });
}