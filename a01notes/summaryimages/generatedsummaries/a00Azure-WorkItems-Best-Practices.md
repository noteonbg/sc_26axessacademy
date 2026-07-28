# Azure Work Items — How to Keep Them Specific




## Part 1. What a Work Item is, in plain words

1. A **Work Item** in Azure Boards is a tracked unit of work — a Task, User Story, Bug, Feature or Epic that the team agrees needs to be done.
2. It is not a chat message and it is not a sticky note. It is the official record of *what* needs to be done, *why*, *who* owns it, and *when* it is done.
3. Your Git commits and Pull Requests should link back to a Work Item (for example `#4521` in the commit message). That is how banking and audit teams later answer "who changed this, and under which approved request?".
4. A vague Work Item produces vague code, vague reviews and vague releases. A specific Work Item produces clear code, fast reviews and a clean audit trail.



---

## Part 2. The rule that stops vagueness

5. Before you click **Save**, read your Work Item title and description out loud and ask:
    - Can a developer who was not in the meeting start this tomorrow without messaging you?
    - Can a tester write a pass/fail check from what is written here?
    - Can a reviewer decide "done" or "not done" without guessing?
6. If the answer to any of those is **no**, the Work Item is still vague. Add the missing detail before you assign it.
7. A useful shortcut — every good Work Item answers these five questions:
    1. **What** exactly must change or be built?
    2. **Where** (which service, screen, API, file or environment)?
    3. **Why** (business reason or defect impact)?
    4. **How will we know it is done** (acceptance criteria)?
    5. **What is out of scope** (so people do not invent extra work)?

---

## Part 3. Best practices

### 3.1 Titles

8. Write the title as a short, specific action — not a topic.
9. Prefer: verb + object + concrete detail.
10. Include a number, rate, screen name or error when you have one.
11. Keep it under roughly 80 characters so it stays readable in Boards boards and sprint views.

### 3.2 Description

12. Start with **Context** in two or three lines — why this exists.
13. Then write **What to do** as numbered steps  a developer can follow.
14. Then write **Acceptance Criteria** as checkable statements (pass/fail).
15. Then write **Out of scope** so people know what not to build.
16. Add **Links** — design doc, API contract, screenshot, related Work Item, failing log.
17. Add **Test notes** for the tester — sample input, expected output, environment.

### 3.3 Acceptance Criteria

18. Acceptance criteria are not a wish list. They are the definition of done.
19. Write them so each line can be marked true or false.
20. Prefer measurable statements: exact rate, exact status code, exact field name, exact message text.
21. Avoid words like "properly", "correctly", "as expected", "handle nicely", "make it better".

### 3.4 Types and sizing

22. Use the right type:
    - **Epic** — large business outcome spanning multiple features.
    - **Feature** — a meaningful chunk of capability.
    - **User Story** — a user-facing need, written from the user's point of view.
    - **Task** — a concrete piece of development or ops work under a story/feature.
    - **Bug** — something that already exists and is wrong.
23. If a Work Item will take more than a couple of days, split it. Small Work Items finish, get reviewed and get released. Giant ones stall.
24. One Work Item = one clear outcome. Do not combine "change the rate" and "redesign the mobile screen" in the same item unless they truly cannot be separated.

### 3.5 Ownership and workflow

25. Every active Work Item should have an **Assignee**. "Someone will do it" means no one will do it.
26. Update the **State** honestly: New → Active → Resolved / Completed. Do not leave finished work in Active.
27. Put the **Iteration / Sprint** and **Area Path** correctly so the board reflects reality.
28. Link related items: the Bug that caused the Task, the Feature that owns the Story, the duplicate Bug you closed.
29. When you commit and raise a PR, put the Work Item ID in the commit message and PR title, for example `#7788 updated savings rate to 3.5%`. Azure will link them automatically.

### 3.6 Bugs specifically

30. A good Bug always includes:
    - Steps to reproduce (numbered).
    - Expected result.
    - Actual result.
    - Environment (dev / UAT / prod), browser or API client, date/time if relevant.
    - Evidence (screenshot, log snippet, request/response, transaction id).
31. Never raise a Bug that only says "EMI is wrong" or "page not working". That is a rumour, not a Bug.

### 3.7 Language and audience

32. Write for a teammate who was on leave during the meeting.
33. Prefer business words for Stories ("customer", "savings account", "EMI") and technical words for Tasks ("endpoint", "column", "null check").
34. Do not hide uncertainty. If a rate is still pending confirmation, write "Blocked on: final rate from Product (expected by Friday)" instead of inventing a number.

---

## Part 4. Suggested Work Item template (copy this)

35. You can paste this into the Description field and fill the blanks:

```text
Context:
- <Why this work exists, in 2-3 lines>

What needs to be done:
1. <Concrete step>
2. <Concrete step>
3. <Concrete step>

Acceptance Criteria:
- [ ] <Checkable statement>
- [ ] <Checkable statement>
- [ ] <Checkable statement>

Out of scope:
- <What we are NOT doing in this Work Item>

Notes / Links:
- Environment:
- Related Work Item:
- Design / API / Screenshot:
- Sample input → expected output:
```

---

## Part 5. DO examples (specific enough to start work)

### Example A — Task (Finance)

36. **Title:** Update savings interest rate from 3.0% to 3.5% in Interest Calculator Service  
37. **Type:** Task  
38. **Description:**
    ```text
    Context:
    RBI guidance changes the standard savings rate from 3.0% to 3.5%,
    effective 1 August. Overnight interest batch must use the new rate.

    What needs to be done:
    1. Change the default RATE in InterestService from 3.0 to 3.5.
    2. Update unit test "100000 for 30 days" expected interest to 291.67.
    3. Confirm config in UAT uses RATE_SOURCE=live, not the old static value.
    4. Link commits and PR to this Work Item.

    Acceptance Criteria:
    - [ ] Code and tests use 3.5 as the standard savings rate.
    - [ ] Unit tests pass locally and in the pipeline.
    - [ ] UAT calculation for principal 1,00,000, 30 days = 291.67.
    - [ ] No change to senior-citizen bonus logic in this Work Item.

    Out of scope:
    - Senior citizen bonus
    - Fixed deposit rates
    - UI copy on the mobile app
    ```
39. **Why this is good:** a developer knows the service, the old value, the new value, the test to update, the environment check, and what not to touch.

### Example B — User Story (Finance)

40. **Title:** As a customer, I can see my estimated EMI before submitting a personal loan application  
41. **Type:** User Story  
42. **Description:**
    ```text
    Context:
    Customers abandon the loan form because they cannot see the monthly
    EMI until after submission. Product wants an estimate on the form.

    What needs to be done:
    1. Add EMI estimate section on Personal Loan application form.
    2. Call EMI Calculator API with amount, tenure and product rate.
    3. Show monthly EMI rounded to 2 decimal places.
    4. Recalculate when amount or tenure changes.

    Acceptance Criteria:
    - [ ] For amount 5,00,000, tenure 36 months, rate 12%, EMI shows 16,607.15.
    - [ ] EMI updates within 1 second after amount or tenure change.
    - [ ] If API fails, show message: "EMI estimate unavailable. Please try again."
    - [ ] Works on Chrome and the bank Android app WebView.

    Out of scope:
    - Final sanction decision
    - Changing interest rate rules
    - iOS native redesign
    ```
43. **Why this is good:** the story is user-focused, but the acceptance criteria are measurable, so developers and testers are not guessing.

### Example C — Bug (Finance)

44. **Title:** Senior citizen bonus adds 0.5% twice for joint accounts with one senior holder  
45. **Type:** Bug  
46. **Description:**
    ```text
    Context:
    Production interest batch on 24 Jul paid excess interest on some
    joint savings accounts.

    Steps to reproduce:
    1. Open joint account ACCT-000045 in UAT.
    2. Ensure primary holder is senior citizen, secondary is not.
    3. Run overnight interest job for 1 day on balance 2,00,000.
    4. Check posted interest amount.

    Expected:
    Base 3.5% + senior bonus 0.5% once = 4.0% effective.

    Actual:
    Bonus applied twice = 4.5% effective.
    Posted interest higher by about 2.74 for that day.

    Environment: UAT, Interest Calculator Service v2.3
    Evidence: batch log id JOB-88921, screenshot attached

    Acceptance Criteria:
    - [ ] Bonus applied only once when at least one holder is senior.
    - [ ] Unit test covers joint account with one senior holder.
    - [ ] Re-run of JOB on sample account posts corrected interest.
    ```
47. **Why this is good:** another developer can reproduce it, see expected vs actual, and know when the fix is truly done.

### Example D — Task for DevOps / Pipeline

48. **Title:** Add Maven build pipeline for Interest Calculator on develop branch  
49. **Description (short form):**
    ```text
    What:
    Create Azure Pipeline YAML that triggers on develop, runs mvn clean install,
    publishes surefire test results, and fails the run if tests fail.

    Acceptance Criteria:
    - [ ] Pipeline triggers on push to develop.
    - [ ] Failed unit test makes the pipeline red.
    - [ ] Build artifact path is documented in the PR description.
    ```

---

## Part 6. DON'T examples (vague — rewrite these)

### Don't 1 — Topic instead of action

50. **Don't write:** `Interest rate`  
51. **Why it fails:** a topic is not work. Nobody knows what to change.  
52. **Do write:** `Update savings interest rate from 3.0% to 3.5% in Interest Calculator Service`

### Don't 2 — Soft adjectives with no measure

53. **Don't write:** `Make EMI calculation better and faster`  
54. **Why it fails:** "better" and "faster" cannot be tested.  
55. **Do write:** `Return EMI from API within 500ms for standard personal-loan payloads; keep response fields amount, tenureMonths, rate, emi unchanged`

### Don't 3 — Bug with no reproduce steps

56. **Don't write:**
    ```text
    Title: Login issue
    Description: User not able to login sometimes. Please check.
    ```
57. **Why it fails:** no user type, no environment, no error message, no steps, no expected result.  
58. **Do write:**
    ```text
    Title: NetBanking login returns 500 when password contains '&'
    Steps:
    1. Open UAT NetBanking login
    2. Enter valid user id
    3. Enter password containing &
    4. Click Login
    Expected: Login succeeds or shows invalid credentials
    Actual: HTTP 500, correlation id C-44192
    ```

### Don't 4 — Mixing multiple outcomes in one item

59. **Don't write:** `Update interest rate, add senior bonus, redesign statement PDF and fix pipeline`  
60. **Why it fails:** four different outcomes, four different reviewers, one blocked item.  
61. **Do write:** four separate Work Items, linked to the same Feature if needed.

### Don't 5 — Hidden decisions

62. **Don't write:** `Use the new rate wherever needed`  
63. **Why it fails:** "wherever needed" invites every developer to invent scope.  
64. **Do write:** `Apply 3.5% only to standard savings overnight batch. Do not change FD, RD or loan rates.`

### Don't 6 — Acceptance criteria that are not checkable

65. **Don't write:**
    ```text
    Acceptance Criteria:
    - Works properly
    - Looks good
    - No issues
    ```
66. **Why it fails:** nobody can mark these true or false.  
67. **Do write:**
    ```text
    Acceptance Criteria:
    - [ ] API returns HTTP 200 for valid payload
    - [ ] API returns HTTP 400 with message "tenureMonths must be > 0" for tenure 0
    - [ ] Unit tests cover tenure 0, 1 and 360
    ```

### Don't 7 — No owner, no sprint, no link

68. **Don't leave:** Assignee empty, Iteration empty, no PR link, no related Bug.  
69. **Why it fails:** the board lies — work looks unplanned and unfinished forever.  
70. **Do:** assign a person, set the sprint, and reference `#WorkItemId` in commits/PRs.

### Don't 8 — Story written like a technical task (or the reverse)

71. **Don't write a User Story as:** `Add column bonus_rate to table ACCOUNT_RATE`  
72. **Don't write a Task as:** `As a user I want happiness with rates`  
73. **Do:** keep Stories user-oriented, keep Tasks implementation-oriented, and link them.

### Don't 9 — Changing scope silently

74. **Don't:** start a Work Item for "rate update" and also rewrite logging, rename APIs and clean unused classes in the same PR without updating the Work Item.  
75. **Do:** either keep the PR limited to the Work Item, or create a new Work Item for the extra cleanup and link it.

### Don't 10 — Closing without evidence

76. **Don't:** move a Bug to Done because "it works on my machine".  
77. **Do:** paste the test result, correlation id, screenshot or pipeline run link into the Work Item discussion before closing.

---

## Part 7. Side-by-side cheat sheet

78. | Vague (Don't) | Specific (Do) |
    |---|---|
    | Fix interest | Update savings RATE from 3.0 to 3.5 in InterestService |
    | Improve performance | EMI API p95 latency under 500ms for 100 concurrent requests in UAT |
    | Handle errors | Return HTTP 400 with code RATE_INVALID when rate < 0 |
    | Update docs | Add rate-change steps to README section "Overnight batch" |
    | Testing needed | Tester verifies 1,00,000 for 30 days returns 291.67 in UAT |
    | As discussed | Link meeting note / Confluence page and restate the decision in the description |
    | Misc changes | Split into separate Tasks with one outcome each |
    | Urgent bug | Include severity, customer impact, reproduce steps and workaround if any |

---

## Part 8. Mini checklist before you click Save

79. Ask yourself:
    1. Is the title an action with a concrete object?
    2. Does the description say what, where and why?
    3. Are acceptance criteria checkable yes/no statements?
    4. Is out of scope written down?
    5. Is there enough evidence or sample data for a tester?
    6. Is there an Assignee and Iteration?
    7. Is this one outcome, not four?
    8. Would a teammate who missed the meeting understand it in under two minutes?
80. If you can tick all eight, the Work Item is specific enough.

---

## Part 9. How this ties to Git and Pipelines

81. Create the Work Item first, then create your feature branch.
82. Put the ID in every commit: `#7788 updated savings rate from 3.0 to 3.5`.
83. Put the same ID in the Pull Request title and description.
84. In the PR, explain how each acceptance criterion was met.
85. After merge, update the Work Item state and add the pipeline run or UAT evidence.
86. That chain — Work Item → branch → commits → PR → pipeline → Done — is what makes delivery traceable in a bank.

---

## Part 10. One-sentence summary

87. A good Azure Work Item is specific enough that a developer can build it, a tester can prove it, and an auditor can trace it — if it still depends on hallway conversation, it is not ready.
