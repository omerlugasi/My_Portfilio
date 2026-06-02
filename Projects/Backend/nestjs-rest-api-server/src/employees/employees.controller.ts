import { Controller, Get, Post, Body, Patch, Param, Delete, Query, Ip } from '@nestjs/common';
import { EmployeesService } from './employees.service';
import { Prisma } from '@prisma/client';
import { Throttle, SkipThrottle } from '@nestjs/throttler';
import { MyLoggerService } from 'src/my-logger/my-logger.service';

@SkipThrottle()
@Controller('employees')
export class EmployeesController {
  constructor(
    private readonly employeesService: EmployeesService,
    private readonly logger: MyLoggerService
  ) {
    this.logger.setContext(EmployeesController.name);
  }

  @Post()
  create(@Body() createEmployeeDto: Prisma.EmployeeCreateInput) {
    this.logger.log(`Creating employee with name ${createEmployeeDto.name}`);
    return this.employeesService.create(createEmployeeDto);
  }

  @SkipThrottle({ default: false })
  @Get()
  findAll(@Ip() ip: string, @Query('role') role?: 'INTERN' | 'ENGINEER' | 'ADMIN') {
    this.logger.log(`Request for all employees\t${ip}`);
    return this.employeesService.findAll(role);
  }

  @Throttle({ short: { ttl: 1000, limit: 1 } })
  @Get(':id')
  findOne(@Param('id') id: string) {
    this.logger.log(`Request for employee with id ${id}`);
    return this.employeesService.findOne(+id);
  }

  @Patch(':id')
  update(@Param('id') id: string, @Body() updateEmployeeDto: Prisma.EmployeeUpdateInput) {
    this.logger.log(`Updating employee with id ${id} `);
    return this.employeesService.update(+id, updateEmployeeDto);
  }

  @Delete(':id')
  remove(@Param('id') id: string) {
    this.logger.log(`Removing employee with id ${id}`);
    return this.employeesService.remove(+id);
  }
}
