# OvertimeCalculation refactoring kata

[![Gitmoji](https://img.shields.io/badge/gitmoji-%20%F0%9F%98%9C%20%F0%9F%98%8D-FFDD67.svg)](https://gitmoji.dev)

## Usage

Run tests: `./gradlew test`

## Problem

Source: [Emily Bache](https://github.com/emilybache/OvertimeCalculation-Refactoring-Kata)

### Description

The `calculateOvertime` function is designed to split overtime hours into two different rates depending on the details
of the particular Assignment and Briefing.

You need to add a new feature, described below. Before that you may want to add tests and refactor the code. Note there
is a branch 'with_tests' if you want to begin with the refactoring straight away.

### New feature

The threshold for overtime hours at rate 2 should be changed from 6 to 4 if the assignment is unionized and the briefing
is foreign.
