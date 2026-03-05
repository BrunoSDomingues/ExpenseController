# Expense Controller
A Kotlin app to view and edit expenses for each day

## Goal
The goal of this application is to serve as a better way to control and view your expenses. There are many apps currently available that do this (including my bank app), but to me they are either too confusing or too expensive, so, I decided to make my own.

## Tech Stack
- Kotlin
- Spring Boot 4.0
- MongoDB
- Swagger UI (available at `http://localhost:8080/swagger-ui.html`)

## Current Functionalities
At the moment, the application has the following functionalities:

### Expenses
- `GET /expenses` — View all expenses
- `GET /expenses/{id}` — View a specific expense by ID
- `POST /expenses` — Add a new expense
- `PUT /expenses/{id}` — Update an existing expense

### Totals
- `GET /expenses/total/day/{date}` — Get total expenses for a specific day (format: DD-MM-YYYY)
- `GET /expenses/total/month` — Get total expenses for the current month
- `GET /expenses/total/category/{category}` — Get total expenses for a category in the current month

### Filtering
- `GET /expenses/category/{category}` — View all expenses for a specific category
- `GET /expenses/payment/{paymentMethod}` — View all expenses for a specific payment method

## Future functionalities
I will be updating this project to add new functionalities. For now, these are the guaranteed future functionalities. This list is subject to changes.

1. View daily average for a month
2. View the most expensive day in a month
3. View the highest expense in a month
4. Custom categories
5. Delete an expense
6. Android app
