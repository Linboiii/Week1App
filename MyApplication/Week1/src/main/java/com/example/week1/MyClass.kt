package com.example.week1

import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)

    val foodNames = ArrayList<String>()
    val foodDescs = ArrayList<String>()
    val foodPrices = ArrayList<Double>()

    foodNames.add("Nasi Goreng")
    foodDescs.add("Fried rice with egg")
    foodPrices.add(150.0)

    foodNames.add("Mie Ayam")
    foodDescs.add("Chicken noodles")
    foodPrices.add(150.0)

    foodNames.add("Es Teh")
    foodDescs.add("Sweet iced tea")
    foodPrices.add(200.0)

    val orderNames = ArrayList<String>()
    val orderDetails = ArrayList<String>()
    val orderTotals = ArrayList<Double>()

    var choice = 0

    while (choice != 7) {
        println("\n--- ORDER SYSTEM ---")
        println("1. Make order")
        println("2. View Orders")
        println("3. View Menu")
        println("4. Add Menu")
        println("5. Edit Menu")
        println("6. Delete Menu")
        println("7. Exit")
        print("Choice: ")

        if (scanner.hasNextInt()) {
            choice = scanner.nextInt()
            scanner.nextLine()
        } else {
            println("Please enter a valid number!")
            scanner.nextLine()
            continue
        }

        if (choice == 1) {
            if (foodNames.isEmpty()) {
                println("Menu is empty! Add items first.")
            } else {
                print("Enter Customer Name: ")
                val custName = scanner.nextLine()

                var orderReceipt = ""
                var grandTotal = 0.0
                var ordering = true

                while (ordering) {
                    println("\n--- MENU ---")
                    for (i in 0 until foodNames.size) {
                        println("${i + 1}. ${foodNames[i]} -$${foodPrices[i]}")
                    }

                    print("Select food number (1-${foodNames.size}): ")
                    var itemNum = -1
                    if (scanner.hasNextInt()) {
                        itemNum = scanner.nextInt()
                        scanner.nextLine()
                    } else {
                        println("Invalid number!")
                        scanner.nextLine()
                        continue
                    }

                    val index = itemNum - 1
                    if (index >= 0 && index < foodNames.size) {
                        print("Enter quantity: ")
                        var qty = 0
                        if (scanner.hasNextInt()) {
                            qty = scanner.nextInt()
                            scanner.nextLine()
                        } else {
                            println("Invalid quantity!")
                            scanner.nextLine()
                            continue
                        }

                        if (qty > 0) {
                            val itemTotal = foodPrices[index] * qty
                            grandTotal += itemTotal
                            orderReceipt += "${foodNames[index]} x$qty$$itemTotal\n"
                            println("Added to cart!")
                        } else {
                            println("Quantity must be more than 0!")
                        }
                    } else {
                        println("Food item not found!")
                    }

                    print("Add another food? (y/n): ")
                    val ans = scanner.nextLine()
                    if (ans.lowercase() != "y") {
                        ordering = false
                    }
                }

                if (grandTotal > 0) {
                    orderNames.add(custName)
                    orderDetails.add(orderReceipt)
                    orderTotals.add(grandTotal)
                    println("Order created successfully!")
                }
            }

        } else if (choice == 2) {
            println("\n--- ALL ORDERS ---")
            if (orderNames.isEmpty()) {
                println("No orders yet.")
            } else {
                for (i in 0 until orderNames.size) {
                    println("\n--- ${orderNames[i]}'s ORDER ---")
                    print(orderDetails[i])
                    println("----------------")
                    println("TOTAL $${orderTotals[i]}")
                }
            }

        } else if (choice == 3) {
            println("\n--- CURRENT MENU ---")
            if (foodNames.isEmpty()) {
                println("No menu items available.")
            } else {
                for (i in 0 until foodNames.size) {
                    println("${i + 1}. ${foodNames[i]} -$${foodPrices[i]}")
                    println("   Description: ${foodDescs[i]}")
                }
            }

        } else if (choice == 4) {
            println("\n--- ADD NEW MENU ---")
            print("Enter food name: ")
            val name = scanner.nextLine()

            print("Enter food description: ")
            val desc = scanner.nextLine()

            print("Enter food price: ")
            var price = -1.0
            if (scanner.hasNextDouble()) {
                price = scanner.nextDouble()
                scanner.nextLine()
            } else {
                scanner.nextLine()
            }

            if (name.isNotEmpty() && desc.isNotEmpty() && price > 0) {
                foodNames.add(name)
                foodDescs.add(desc)
                foodPrices.add(price)
                println("New menu added!")
            } else {
                println("Failed to add! Make sure inputs are not empty and price is greater than 0.")
            }

        } else if (choice == 5) {
            println("\n--- EDIT MENU ---")
            if (foodNames.isEmpty()) {
                println("Menu is empty.")
            } else {
                for (i in 0 until foodNames.size) {
                    println("${i + 1}.${foodNames[i]}")
                }
                print("Enter food number to edit: ")
                var num = -1
                if (scanner.hasNextInt()) {
                    num = scanner.nextInt()
                    scanner.nextLine()
                } else {
                    scanner.nextLine()
                }

                val index = num - 1
                if (index >= 0 && index < foodNames.size) {
                    print("Enter new name: ")
                    val newName = scanner.nextLine()

                    print("Enter new description: ")
                    val newDesc = scanner.nextLine()

                    print("Enter new price: ")
                    var newPrice = -1.0
                    if (scanner.hasNextDouble()) {
                        newPrice = scanner.nextDouble()
                        scanner.nextLine()
                    } else {
                        scanner.nextLine()
                    }

                    if (newName.isNotEmpty() && newDesc.isNotEmpty() && newPrice > 0) {
                        foodNames[index] = newName
                        foodDescs[index] = newDesc
                        foodPrices[index] = newPrice
                        println("Menu updated!")
                    } else {
                        println("Invalid details. Edit cancelled.")
                    }
                } else {
                    println("Invalid menu number!")
                }
            }

        } else if (choice == 6) {
            println("\n--- DELETE MENU ---")
            if (foodNames.isEmpty()) {
                println("Menu is empty.")
            } else {
                for (i in 0 until foodNames.size) {
                    println("${i + 1}.${foodNames[i]}")
                }
                print("Enter food number to delete: ")
                var num = -1
                if (scanner.hasNextInt()) {
                    num = scanner.nextInt()
                    scanner.nextLine()
                } else {
                    scanner.nextLine()
                }

                val index = num - 1
                if (index >= 0 && index < foodNames.size) {
                    foodNames.removeAt(index)
                    foodDescs.removeAt(index)
                    foodPrices.removeAt(index)
                    println("Menu item deleted!")
                } else {
                    println("Invalid menu number!")
                }
            }

        } else if (choice == 7) {
            println("Exiting application...")
        } else {
            println("Invalid choice! Choose between 1 and 7.")
        }
    }
}