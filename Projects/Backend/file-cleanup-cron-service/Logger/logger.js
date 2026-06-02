import winston from "winston";
import "winston-daily-rotate-file";
import os from "os";

const { transports, format } = winston;

const loglevel = process.env.LOG_LEVEL || "info";

const customFormat = format.combine(
  format((info) => {
    info.level = info.level.toUpperCase();
    return info;
  })(),
  format.timestamp({ format: "YYYY-MM-DD HH:mm:ss" }),
  format.printf(({ level, timestamp, message, ...meta }) => {
    return JSON.stringify({
      level,
      time: timestamp,
      pid: process.pid,
      hostname: os.hostname(),
      msg: message,
      meta,
    });
  })
);

const fileTransport = new transports.DailyRotateFile({
  dirname: process.env.LOG_PATH || 'logs',
  filename: `${process.env.PROJECT_NAME || 'file-cleanup-cron-service'}_%DATE%.log`,
  datePattern: "DD-MM-YYYY",
  auditFile: "logs/audit.json",
  maxSize: "20m",
  maxFiles: "30d",
});

const logger = winston.createLogger({
  level: loglevel,
  format: customFormat,
  transports: [new transports.Console(), fileTransport],
});

export { logger };