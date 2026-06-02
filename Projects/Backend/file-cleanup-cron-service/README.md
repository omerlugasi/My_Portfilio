# File Cleanup Cron Service

A configurable Node.js cron service for automated file cleanup tasks.

The service scans configured folders, applies age-based rules, and performs cleanup actions such as deleting old files or moving files to a backup folder.

## Features

- Cron-based scheduled execution
- Config-driven cleanup rules
- Age-based file filtering
- File deletion and move actions
- Backup-folder cleanup
- Structured logging with daily log rotation
- Generic folder and extension configuration

## Project Structure

```text
file-cleanup-cron-service/
├── Config/
│   ├── cleanupRules.example.json
│   └── getConfig.js
├── Helpers/
│   ├── fileActions.js
│   └── ruleMatcher.js
├── Jobs/
│   ├── backupCleanupJob.js
│   ├── folderJob.js
│   └── incomingCleanupJob.js
├── Logger/
│   └── logger.js
├── Scheduler/
│   └── cronScheduler.js
├── Services/
│   └── cleanupService.js
├── index.js
├── package.json
└── README.md
```

## Configuration

The real configuration file is intentionally ignored by Git to avoid exposing environment-specific paths.

Create a local config file from the example:

```bash
cp Config/cleanupRules.example.json Config/cleanupRules.json
```

Then edit `Config/cleanupRules.json` according to your local folders and cleanup rules.

Example configuration:

```json
{
  "schedule": "0 20 * * *",
  "folders": {
    "main": "./data/incoming",
    "backup": "./data/backup"
  },
  "incomingRules": [
    {
      "name": "delete-old-xml",
      "type": "age",
      "extensions": ["xml"],
      "olderThanDays": 1,
      "ageBy": "birthtime",
      "action": "delete"
    }
  ],
  "backupRules": [
    {
      "name": "delete-old-backup-files",
      "type": "age",
      "extensions": ["*"],
      "olderThanDays": 7,
      "ageBy": "birthtime",
      "action": "delete"
    }
  ]
}
```

## Rule Fields

| Field | Description |
|---|---|
| `name` | Rule name used for logging |
| `type` | Rule type. Currently supports `age` |
| `extensions` | File extensions to match, or `*` for all files |
| `olderThanDays` | Minimum file age in days before action is applied |
| `ageBy` | File timestamp field, such as `birthtime` or `mtime` |
| `action` | Action to perform: `delete` or `move` |
| `targetFolderKey` | Target folder key for move actions |

## Installation

```bash
npm install
```

## Run

```bash
npm start
```

For development:

```bash
npm run dev
```

## Environment Variables

Optional environment variables:

```text
LOG_LEVEL=info
LOG_PATH=logs
PROJECT_NAME=file-cleanup-cron-service
```
