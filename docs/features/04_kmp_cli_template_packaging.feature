Feature: KMP CLI Template Packaging
  As a developer using KMP CLI to scaffold a new Kotlin Multiplatform app
  I want to run `kmp kit create zora-community` with zero license required
  So that I get a fully scaffolded, compilable project tailored with my app name and package namespace

  Scenario: Scaffold project without license requirements
    Given a developer executes `kmp kit create zora-community --name MyAutoGarage --package com.example.autogarage`
    When the CLI executes the command
    Then the project is scaffolded into directory "MyAutoGarage" without requiring a license key
    And all package namespaces, bundle identifiers, and root module names are replaced with "com.example.autogarage"

  Scenario: Bundle manifest and template metadata
    Given the template directory inside KMP CLI
    When inspected via `kmp kit list`
    Then `zora-community` is reported with tier "COMMUNITY", price "$0", and license "Apache 2.0"
    And status indicates "UNLOCKED" on all machines
