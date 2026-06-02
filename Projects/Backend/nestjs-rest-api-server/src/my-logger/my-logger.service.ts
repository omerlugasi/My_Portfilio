import { ConsoleLogger, Injectable } from '@nestjs/common';
import * as fs from 'fs';
import * as path from 'path';
import {promises as fsPromises} from 'fs';


@Injectable()
export class MyLoggerService extends ConsoleLogger {
    async logToFile(entry) {
        const formattedEntry = `${Intl.DateTimeFormat('he-IL', {
            dateStyle: 'short',
            timeStyle: 'short',
            timeZone: 'Asia/Jerusalem',
        }).format(new Date())}\t${entry}\n`;

        try {
            if (!fs.existsSync(path.join(__dirname, '..', '..', 'logs'))) {
                await fsPromises.mkdir(path.join(__dirname, '..', '..', 'logs'),);
            }

            await fsPromises.appendFile(
                path.join(__dirname, '..', '..', 'logs', 'myLogFile.log',),
                formattedEntry
            );

        } catch (e) {
            if (e instanceof Error) {
                super.error(e.message);
            }
        }
    }
        
    log(message: any, context?: string) {
        const usedContext = context ?? this.context;
        const entry = `${usedContext}\t${message}`;
        this.logToFile(entry);
        super.log(message, usedContext);
    }
    
    error(message: any, trace?: string, context?: string) {
        const usedContext = context ?? this.context;
        const entry = `${usedContext}\t${message}\t${trace}`;
        this.logToFile(entry);
        super.error(message, trace, usedContext);
    }
}
