import { logger } from '../Logger/logger.js';
import { runIncomingCleanupJob } from '../Jobs/incomingCleanupJob.js';
import { runBackupCleanupJob } from '../Jobs/backupCleanupJob.js';

export async function runCleanupService() {
  logger.info('Job started');

  const [incomingSummary, backupSummary] = await Promise.all([
    runIncomingCleanupJob(),
    runBackupCleanupJob()
  ]);

  logger.info('Incoming cleanup summary', incomingSummary);
  logger.info('Backup cleanup summary', backupSummary);
  logger.info('Job completed');
}