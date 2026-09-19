package com.example.week1

import java.util.Scanner
import kotlin.random.Random

fun main() {
    val scanner = Scanner(System.`in`)

    // Player stats
    var wizardName = ""
    var isEvolved = false

    var maxHp = 50
    var currentHp = 50
    var maxMana = 30
    var currentMana = 30

    var killsCount = 0
    val killsToEvolve = 5
    var lifesteal = 0

    var hpPotions = 5
    var mpPotions = 5

    // Get wizard name
    print("What's your name? ")
    wizardName = scanner.nextLine().trim()
    while (wizardName.isEmpty()) {
        print("Please enter a valid name: ")
        wizardName = scanner.nextLine().trim()
    }
    println("Good luck, $wizardName! You're gonna need it!")

    var mainChoice = 0

    while (mainChoice != 3) {
        println("\nWhat're you going to do?")
        println("1. View Stats")
        println("2. Enter battle")
        println("3. Exit")
        print("Choice: ")

        if (scanner.hasNextInt()) {
            mainChoice = scanner.nextInt()
            scanner.nextLine() // Clear buffer
        } else {
            println("Please enter a number!")
            scanner.nextLine()
            continue
        }

        if (mainChoice == 1) {
            // VIEW STATS MENU
            var inStats = true
            while (inStats) {
                println("\n——— $wizardName's STATS ———")
                println("HP: $currentHp/ $maxHp")
                println("Mana: $currentMana/ $maxMana")
                if (!isEvolved) {
                    println("Kills needed to evolve: $killsCount/ $killsToEvolve")
                } else {
                    println("Status: EVOLVED (Strong Wizard)")
                    println("Lifesteal: $lifesteal")
                }
                println("Mana Potions held: $mpPotions")
                println("Health Potions held: $hpPotions")
                println("—————————————————")
                println("a. Drink Mana Potion")
                println("b. Drink Health Potion")
                println("c. Rename self")
                println("d. Back")
                print("Choice: ")

                val statChoice = scanner.nextLine().trim().lowercase()

                if (statChoice == "a") {
                    if (mpPotions > 0) {
                        mpPotions--
                        currentMana += 15
                        if (currentMana > maxMana) currentMana = maxMana
                        println("Drank Mana Potion! MP restored to $currentMana.")
                    } else {
                        println("No Mana Potions left!")
                    }
                } else if (statChoice == "b") {
                    if (hpPotions > 0) {
                        hpPotions--
                        currentHp += 25
                        if (currentHp > maxHp) currentHp = maxHp
                        println("Drank Health Potion! HP restored to $currentHp.")
                    } else {
                        println("No Health Potions left!")
                    }
                } else if (statChoice == "c") {
                    print("Enter new name: ")
                    val newName = scanner.nextLine().trim()
                    if (newName.isNotEmpty()) {
                        wizardName = newName
                        println("Name changed to $wizardName!")
                    } else {
                        println("Name cannot be empty!")
                    }
                } else if (statChoice == "d") {
                    inStats = false
                } else {
                    println("Invalid choice! Choose a, b, c, or d.")
                }
            }

        } else if (mainChoice == 2) {
            // ENTER BATTLE
            val types = arrayOf("Fire", "Water", "Grass")
            val enemyType = types[Random.nextInt(0, 3)]
            val enemyName = enemyType + "mon"

            var enemyMaxHp = 30
            var enemyHp = 30

            println("\nA wild $enemyName appeared!")
            var inBattle = true

            while (inBattle) {
                println("\n——— BATTLE ———")
                println("HP: $currentHp/ $maxHp")
                println("Mana: $currentMana/ $maxMana")
                println("HP Potions: $hpPotions")
                println("MP Potions: $mpPotions")
                println("$enemyName")
                println("HP: $enemyHp/ $enemyMaxHp")
                println("Type: $enemyType")
                println("——————————")
                println("a. Fire Attack")
                println("b. Water Attack")
                println("c. Grass Attack")
                println("d. Drink potion")
                println("e. Run")
                print("Choice: ")

                val battleChoice = scanner.nextLine().trim().lowercase()

                if (battleChoice == "a" || battleChoice == "b" || battleChoice == "c") {
                    // Check Mana
                    if (currentMana < 10) {
                        println("Not enough Mana to cast a spell! (10 MP needed)")
                        continue
                    }

                    currentMana -= 10

                    var playerSpellType = ""
                    if (battleChoice == "a") playerSpellType = "Fire"
                    if (battleChoice == "b") playerSpellType = "Water"
                    if (battleChoice == "c") playerSpellType = "Grass"

                    // Calculate Base Damage
                    var damage = 10.0
                    if (isEvolved) damage *= 1.5 // 1.5x damage for Strong Wizard

                    // Type Matchups (2x damage)
                    var isSuperEffective = false
                    if (playerSpellType == "Fire" && enemyType == "Grass") isSuperEffective = true
                    if (playerSpellType == "Water" && enemyType == "Fire") isSuperEffective = true
                    if (playerSpellType == "Grass" && enemyType == "Water") isSuperEffective = true

                    if (isSuperEffective) {
                        damage *= 2.0
                        println("It's super effective!")
                    }

                    val finalDamage = damage.toInt()
                    enemyHp -= finalDamage
                    println("You cast $playerSpellType Attack and dealt$finalDamage damage!")

                    // Apply Lifesteal if Evolved
                    if (isEvolved && lifesteal > 0) {
                        currentHp += lifesteal
                        if (currentHp > maxHp) currentHp = maxHp
                        println("Lifesteal restored $lifesteal HP!")
                    }

                    // Check if Enemy Defeated
                    if (enemyHp <= 0) {
                        println("\nYou defeated $enemyName!")
                        killsCount++

                        if (isEvolved) {
                            lifesteal++
                            println("Lifesteal increased to $lifesteal!")
                        } else {
                            println("Kills: $killsCount/$killsToEvolve")
                            if (killsCount >= killsToEvolve) {
                                isEvolved = true
                                lifesteal = 1
                                maxHp = (maxHp * 1.5).toInt()
                                maxMana = (maxMana * 1.5).toInt()
                                currentHp = maxHp
                                currentMana = maxMana
                                println("\n************************************************")
                                println("CONGRATULATIONS! YOU EVOLVED INTO A STRONG WIZARD!")
                                println("Max HP and Max Mana increased by 1.5x!")
                                println("Gained Lifesteal stat starting at 1!")
                                println("************************************************")
                            }
                        }
                        inBattle = false
                        break
                    }

                    // Enemy Counter-Attacks
                    println("$enemyName attacks you for 10 damage!")
                    currentHp -= 10

                    // Check Player Death
                    if (currentHp <= 0) {
                        println("\n==================================")
                        println("YOU DIED! Game Resetting...")
                        println("==================================")
                        // Reset All Stats
                        isEvolved = false
                        maxHp = 50
                        currentHp = 50
                        maxMana = 30
                        currentMana = 30
                        killsCount = 0
                        lifesteal = 0
                        hpPotions = 5
                        mpPotions = 5
                        inBattle = false
                    }

                } else if (battleChoice == "d") {
                    println("\nWhich potion?")
                    println("1. Health Potion (Restores 25 HP)")
                    println("2. Mana Potion (Restores 15 MP)")
                    print("Choice: ")
                    val potChoice = scanner.nextLine().trim()

                    if (potChoice == "1") {
                        if (hpPotions > 0) {
                            hpPotions--
                            currentHp += 25
                            if (currentHp > maxHp) currentHp = maxHp
                            println("Drank HP potion! HP is now $currentHp.")
                        } else {
                            println("No HP potions left!")
                        }
                    } else if (potChoice == "2") {
                        if (mpPotions > 0) {
                            mpPotions--
                            currentMana += 15
                            if (currentMana > maxMana) currentMana = maxMana
                            println("Drank MP potion! MP is now $currentMana.")
                        } else {
                            println("No MP potions left!")
                        }
                    } else {
                        println("Invalid potion choice!")
                    }

                } else if (battleChoice == "e") {
                    println("You fled from battle!")
                    inBattle = false
                } else {
                    println("Invalid choice! Enter a, b, c, d, or e.")
                }
            }

        } else if (mainChoice == 3) {
            println("Exiting game... Goodbye!")
        } else {
            println("Invalid choice! Please select 1, 2, or 3.")
        }
    }
}
