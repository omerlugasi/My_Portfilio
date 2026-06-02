import cron from 'node-cron';
import config from '../Config/getConfig.js';
import { runCleanupService } from '../Services/cleanupService.js';

export function startScheduler() {
  cron.schedule(config.schedule, async () => {
    await runCleanupService();
  });
}