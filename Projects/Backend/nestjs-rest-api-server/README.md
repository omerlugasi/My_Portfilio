# NestJS REST API Server

A backend REST API server built with NestJS and TypeScript.

This project demonstrates core backend development concepts including modular architecture, REST controllers, DTO validation, Prisma ORM integration, PostgreSQL database access, request throttling, custom logging, and global exception handling.

## Main Features

- REST API server with NestJS
- TypeScript-based backend structure
- Employee CRUD operations using Prisma and PostgreSQL
- User CRUD module for practicing basic REST patterns
- DTO validation
- Global exception filter
- Custom file logger
- Request rate limiting with `@nestjs/throttler`
- Prisma schema and migration setup

## Technologies Used

- NestJS
- TypeScript
- Node.js
- Prisma ORM
- PostgreSQL
- Class Validator
- Class Transformer

## Project Structure

```text
src/
├── employees/              # Employee REST module using Prisma
├── users/                  # User REST module
├── database/               # Prisma database service/module
├── my-logger/              # Custom logging service
├── all-exceptions.filter.ts
├── app.module.ts
└── main.ts
```

## Setup

Install dependencies:

```bash
npm install
```

Create a local `.env` file based on `.env.example`:

```bash
cp .env.example .env
```

Run Prisma migration/generation if needed:

```bash
npx prisma generate
npx prisma migrate dev
```

Start the development server:

```bash
npm run start:dev
```

## Notes

This project was created as a backend learning project focused on practicing NestJS fundamentals and backend infrastructure patterns.
