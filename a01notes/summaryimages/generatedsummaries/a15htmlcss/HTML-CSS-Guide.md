# HTML & CSS — Fresh Hire Guide

Source deck: `HTML and CSS.pdf`  
Audience: new college hires joining a bank / financial IT team

You may not become a full-time UI designer. Still, almost every banking journey starts on a webpage: login, open account, card block, loan apply. Learn enough HTML/CSS to build safe, clear forms.

---

## How to use this file

1. Read one topic.
2. Open the finance example in your mind as a real customer screen.
3. Try the syntax in a `.html` file and open it in Chrome.
4. Follow the industry best practice — banks care about trust and data safety on every page.

---

# Topic 1. Web Designer vs Web Programmer

## Simple explanation
1. **Web Designer** focuses on look, layout, brand, usability.
2. **Web Programmer** focuses on behavior, validation, integration with backend/APIs.

In a bank project both must work together. A beautiful page that posts password in the URL is still a failure.

## Finance example
Open Savings Account page:
1. Designer decides field order, spacing, button style, SC brand color.
2. Programmer ensures validation, POST method, and API call to account-opening service.

## Industry best practice
1. Never “finish UI” without security review on forms that collect PII.
2. Accessibility matters: clear labels help customers and auditors.
3. Match brand guidelines; banking sites should look calm and trustworthy, not noisy.

---

# Topic 2. What is a Website / Client-Server

## Simple explanation
1. Browser = client
2. Server = where page/app lives
3. Customer types URL → browser sends HTTP request → server returns HTML/CSS/JS

## Finance example
Customer opens `https://bank.example.com/open-account`.  
Browser requests the form. After submit, server receives customer details and starts account opening workflow.

## Syntax mindset

```text
Browser  --HTTP request-->  Bank Web Server
Browser  <--HTML/CSS------  Bank Web Server
```

## Industry best practice
1. Use HTTPS everywhere for banking pages.
2. Do not hardcode secrets in frontend pages.
3. Assume anything in browser can be inspected by the user.

---

# Topic 3. HTML basics

## Simple explanation
HTML gives structure using tags.

```html
<!DOCTYPE html>
<html>
<head>
  <title>SC Bank</title>
</head>
<body>
  <h1>Welcome to SC Bank</h1>
  <p>Open a savings account online.</p>
</body>
</html>
```

## Finance example
A bank page needs heading, short instructions, form, and footer with helpdesk contact.

## Industry best practice
1. Use semantic structure (`header`, `main`, `form`, `footer`).
2. One page = one main job. Do not dump 20 offers on an account-opening first screen.
3. Keep copy clear: “Account Number”, not internal jargon like “CIF surrogate”.

---

# Topic 4. Elements and attributes

## Simple explanation
1. Element = tag + content
2. Attribute = extra info on tag (`id`, `class`, `name`, `type`)

`id` should be unique. `class` can be shared for styling.

## Finance example

```html
<input id="customerMobile" class="form-input" name="mobile" type="text">
<label for="customerMobile">Mobile Number</label>
```

## Industry best practice
1. `name` matters for form submit. Backend reads `name`, not the label text.
2. Meaningful `id` helps testing automation (Selenium/Playwright).
3. Do not reuse same `id` on multiple fields.

---

# Topic 5. Forms (the most important HTML skill for bank IT)

## Simple explanation
Forms collect user input and send it to a server.

Common controls:
1. text box
2. email
3. radio
4. checkbox
5. dropdown (`select`)
6. textarea
7. submit / reset buttons

## Finance example — Open Account form

```html
<form action="/open-account" method="post">
  <label for="firstName">First Name</label>
  <input id="firstName" name="firstName" type="text" required>

  <label for="accountType">Account Type</label>
  <select id="accountType" name="accountType" required>
    <option value="SAVINGS">Savings</option>
    <option value="CURRENT">Current</option>
  </select>

  <button type="submit">Submit Application</button>
</form>
```

## Industry best practice
1. Every input needs a visible label.
2. Required banking fields must be marked required.
3. Reset button should not be easy to click accidentally next to Submit.

---

# Topic 6. GET vs POST

## Simple explanation
1. **GET** — data goes in URL  
2. **POST** — data goes in request body

## Finance example
1. GET is fine for “search branch by city”
2. POST is required for “submit PAN, mobile, email for account opening”

Bad idea:

```text
/open-account?pan=ABCDE1234F&mobile=9876543210
```

That can sit in browser history and logs.

## Syntax

```html
<form action="welcome.html" method="get">  <!-- visible in URL -->
<form action="welcome.html" method="post"> <!-- better for personal data -->
```

## Industry best practice
1. Any personal/financial data → POST.
2. Never put passwords, OTPs, PAN, Aadhaar in query string.
3. Even with POST, server must use HTTPS.

---

# Topic 7. HTML5 input types

## Simple explanation
HTML5 gives smarter input types so mobile keyboards and basic checks improve.

## Finance example

```html
<input type="email" name="email" required>
<input type="tel" name="mobile" required>
<input type="number" name="depositAmount" min="1000" step="100" required>
<input type="date" name="dob" required>
```

## Industry best practice
1. Use correct type (`email`, `number`, `date`) to reduce bad data.
2. Still validate again on server. Browser checks can be bypassed.
3. For amount, decide decimals carefully (`step="0.01"` if paise allowed).

---

# Topic 8. Validation attributes (`required`, `pattern`)

## Simple explanation
Validation stops obviously wrong data before submit.

## Finance example
Name only alphabets. Mobile 10 digits starting 6-9. Email required.

```html
<input
  name="firstName"
  required
  pattern="[A-Za-z ]+"
  title="Only alphabets and spaces">

<input
  name="mobile"
  required
  pattern="[6-9][0-9]{9}"
  maxlength="10"
  title="Enter valid 10-digit mobile">

<input
  name="email"
  type="email"
  required>
```

## Industry best practice
1. Frontend validation = good UX.
2. Backend validation = mandatory control.
3. Show human-friendly error text. Customers panic on banking sites when errors are unclear.

---

# Topic 9. Semantic HTML

## Simple explanation
Semantic tags describe meaning, not just layout.

Examples: `header`, `nav`, `main`, `section`, `article`, `footer`

## Finance example

```html
<header>SC Bank</header>
<nav>Home | Accounts | Loans | Support</nav>
<main>
  <section>Open Savings Account</section>
</main>
<footer>Need help? Call 1800-XXX-XXXX</footer>
```

## Industry best practice
1. Helps accessibility and maintainability.
2. Screen readers work better — important for inclusive banking.
3. Cleaner structure for large enterprise portals.

---

# Topic 10. What is CSS? (start very slow)

## Simple explanation
HTML builds the structure.  
CSS changes how that structure **looks**.

Remember:
1. HTML = skeleton
2. CSS = dress code / paint / spacing

If your form works but looks plain, that is an HTML success and a CSS gap.  
For the Week 3 assignment, you need both.

## The smallest CSS rule in the world

```css
h1 {
  color: blue;
}
```

What each part means:
1. `h1` — **selector** = which HTML tag to style
2. `{ ... }` — **declaration block** = start/end of styles for that selector
3. `color` — **property** = what you want to change
4. `blue` — **value** = the new setting
5. `;` — every CSS line inside `{ }` should end with semicolon

Read it in English:
> “Find all h1 headings and make their text blue.”

## Finance example
Make the bank page heading use brand blue:

```css
h1 {
  color: #0b3d91;
}
```

`#0b3d91` is a hex color code (RRGGBB). Many bank brands use a dark blue.

## Industry best practice
1. Learn to read CSS out loud in English. That is how seniors debug.
2. One idea per rule when learning.
3. Do not copy huge CSS frameworks for this assignment. Keep it simple.

---

# Topic 11. Where to write CSS (Inline / Internal / External)

## Simple explanation
There are 3 places CSS can live.

### 1) Inline CSS — style on the HTML tag itself

```html
<p style="color: red; font-size: 18px;">Payment failed</p>
```

Meaning:
1. `style="..."` is an HTML attribute
2. styles apply only to this one tag
3. useful for tiny demos, bad for whole websites

### 2) Internal CSS — style block in the same HTML file

```html
<head>
  <style>
    h1 {
      color: #0b3d91;
    }
  </style>
</head>
```

Meaning:
1. `<style>` goes in `<head>`
2. styles apply to this page only
3. okay for one-page practice

### 3) External CSS — separate `.css` file (best for assignment)

`styles.css`:

```css
h1 {
  color: #0b3d91;
}
```

HTML:

```html
<head>
  <link rel="stylesheet" href="styles.css">
</head>
```

Meaning of the link tag:
1. `<link>` — connects another file to this page
2. `rel="stylesheet"` — relationship is “this is my CSS”
3. `href="styles.css"` — file name/path of CSS

## Finance example
`form.html`, `welcome.html`, and `sc-website.html` all share one `styles.css`.  
Change button color once → all bank pages update.

## What you should do in the assignment
Use **external CSS** (`styles.css`) like the solution folder.

## Industry best practice
1. External CSS is normal in real projects.
2. Inline CSS becomes hard to maintain.
3. Keep HTML for structure, CSS for look.

---

# Topic 12. CSS Selectors (very important)

## Simple explanation
A selector answers: **which elements should get this style?**

## 1) Universal selector `*`

```css
* {
  font-family: Arial, Helvetica, sans-serif;
}
```

Meaning:
1. `*` = all elements
2. set one common font for the whole page

Assignment use: start of `styles.css`.

## 2) Element selector

```css
body {
  background: #f4f7fb;
  color: #12263a;
}

label {
  font-weight: bold;
}

input {
  width: 100%;
}
```

Meaning:
1. `body` styles the whole page background/text
2. `label` makes all labels bold
3. `input` makes all text boxes full width of the form card

## 3) Class selector `.name`

HTML:

```html
<div class="card">...</div>
<a class="btn">Open Account</a>
```

CSS:

```css
.card {
  background: white;
}

.btn {
  background: #0b3d91;
  color: white;
}
```

Meaning:
1. `.card` means “elements whose class contains card”
2. class is reusable on many tags
3. in HTML use `class="card"`; in CSS use `.card`

## 4) ID selector `#name`

HTML:

```html
<form id="openAccountForm">...</form>
```

CSS:

```css
#openAccountForm {
  max-width: 520px;
}
```

Meaning:
1. `#` targets one unique id
2. one id should appear only once in a page

## 5) Group selector

```css
button, .btn {
  padding: 10px 16px;
}
```

Meaning:
1. style both `button` tags and `.btn` links with same padding
2. comma means “and also”

## Finance example

```html
<button class="btn">Submit Application</button>
<a class="btn secondary" href="form.html">Back</a>
```

```css
.btn { background: #0b3d91; color: white; }
.btn.secondary { background: #5b6b7c; }
```

Meaning of `.btn.secondary`:
1. element must have class `btn` AND class `secondary`
2. no space between `.btn` and `.secondary`

## Industry best practice
1. Prefer classes for reusable UI (buttons, cards).
2. Use ids sparingly.
3. Name by purpose: `.btn`, `.card`, `.error` — not `.blue1`.

---

# Topic 13. Colors, units, and common property values

## Colors

```css
color: white;          /* text color by name */
color: #0b3d91;        /* hex color */
background: #f4f7fb;   /* page/card background */
```

Meaning:
1. `color` changes text color
2. `background` / `background-color` changes fill behind content
3. hex `#0b3d91` = custom bank blue

## Size units you need for assignment

```css
font-size: 22px;     /* absolute pixels */
width: 90%;          /* percent of parent width */
max-width: 520px;    /* do not grow wider than 520px */
margin: 20px;        /* 20 pixels space outside */
padding: 16px;       /* 16 pixels space inside */
```

Meaning:
1. `px` = pixels, easy for beginners
2. `%` = relative to parent
3. `max-width` stops the form becoming too wide on big screens

## Finance example
A form card of `width: 90%` and `max-width: 520px` looks good on laptop and still readable.

---

# Topic 14. Box Model (content, padding, border, margin)

## Simple explanation
Every element is a box.

```text
[ margin ]
  [ border ]
    [ padding ]
      [ content ]
```

1. **content** — text/image itself
2. **padding** — empty space inside border
3. **border** — line around the box
4. **margin** — empty space outside border

## Syntax line by line

```css
.card {
  background: white;                 /* fill color of card */
  margin: 20px auto;                 /* 20px top/bottom; left/right centered */
  padding: 20px;                     /* space inside card around fields */
  width: 90%;                        /* take 90% of page width */
  max-width: 520px;                  /* but never more than 520px */
  border-radius: 8px;                /* round the corners */
  box-shadow: 0 2px 8px rgba(0,0,0,0.08); /* soft shadow */
}
```

What `margin: 20px auto` means:
1. first value `20px` = top and bottom margin
2. second value `auto` = browser centers the block horizontally

What `border-radius: 8px` means:
1. soft rounded corners, modern card look

What `box-shadow: 0 2px 8px rgba(0,0,0,0.08)` means:
1. `0` = no left/right shift
2. `2px` = slightly down
3. `8px` = blur amount
4. `rgba(0,0,0,0.08)` = light black shadow with 8% opacity

## Also learn this beginner life-saver

```css
* {
  box-sizing: border-box;
}
```

Meaning:
1. by default, width may ignore padding/border and surprise you
2. `border-box` makes width include padding + border
3. forms become much easier to control

## Finance example
Account form inside a white card with padding so fields are not stuck to edges. Customers trust neat forms more.

## Industry best practice
1. Use consistent spacing: 8, 12, 16, 20, 24 px.
2. Crowded money screens cause wrong clicks.
3. Center main forms; leave side space.

---

# Topic 15. Font properties (from classroom CSS)

## Simple explanation
Font properties control text readability.

```css
body {
  font-family: Arial, Helvetica, sans-serif;
}

.brand {
  font-size: 22px;
  font-weight: bold;
}

.hint {
  font-size: 14px;
  color: #445;
}

label {
  font-weight: bold;
}
```

Meaning line by line:
1. `font-family` — which font to use; if Arial missing, try Helvetica, else default sans-serif
2. `font-size: 22px` — text height
3. `font-weight: bold` — make text thicker (labels/brand)
4. smaller `font-size` for hints/help text

## Finance example
1. Brand name big and bold
2. Field labels bold
3. Helper text smaller and gray

## Industry best practice
1. Body text should be easy to read (usually 14px–16px+)
2. Do not use fancy fonts that reduce trust
3. Error text should be clearly visible, usually red

---

# Topic 16. Border properties

## Simple explanation
Borders draw outlines around inputs, cards, buttons.

```css
input {
  border: 1px solid #c9d4e3;
  border-radius: 4px;
}

button {
  border: none;
}
```

What `border: 1px solid #c9d4e3` means:
1. `1px` — thickness
2. `solid` — continuous line (also possible: dotted/dashed)
3. `#c9d4e3` — border color

What `border: none` means:
1. remove default browser button border
2. useful when you style button with background color

## Finance example
Input boxes with light border look like clear fields to type account details into.

## Shorthand vs long form

```css
/* shorthand */
border: 1px solid #c9d4e3;

/* same meaning, longer */
border-width: 1px;
border-style: solid;
border-color: #c9d4e3;
```

For assignment, shorthand is enough.

---

# Topic 17. Display property (inline / block / none / inline-block)

## Simple explanation
`display` controls how an element sits in the page flow.

### 1) `display: block`
Takes full available width. Starts on a new line.

```css
label {
  display: block;
}
```

Meaning: each label goes above its input (perfect for forms).

### 2) `display: inline`
Sits in the same line as neighbors; width/height tricks are limited.

```css
nav a {
  /* links are inline by default */
}
```

### 3) `display: inline-block`
Sits in a line like inline, but accepts width/height/padding like block.

```css
.btn {
  display: inline-block;
  padding: 10px 16px;
}
```

Meaning: good for buttons that are actually `<a>` links.

### 4) `display: none`
Hides element completely.

```css
.hidden-error {
  display: none;
}
```

## Finance example
Bank form labels as block so “Mobile Number” appears above the box, not beside in a messy way.

## Industry best practice
1. Forms: labels usually `block`
2. Buttons/links: `inline-block` is beginner-friendly
3. Do not hide legal consent with `display:none`

---

# Topic 18. Width, padding, margin for forms (assignment critical)

## Syntax used in your solution

```css
input {
  width: 100%;      /* input spans full card width */
  padding: 10px;     /* space inside input around typed text */
}

button, .btn {
  margin-top: 16px;     /* space above button */
  margin-right: 8px;    /* space between two buttons */
  padding: 10px 16px;   /* top/bottom 10px, left/right 16px */
}
```

What `padding: 10px 16px` means:
1. 2 values = vertical horizontal
2. top+bottom = 10px
3. left+right = 16px

What `margin: 20px auto` means again:
1. vertical 20px
2. horizontal auto = center

## Finance example
Submit and Reset buttons need gap (`margin-right`) so users do not click Reset by mistake.

---

# Topic 19. Background and gradients (for website hero)

## Simple explanation

```css
.top {
  background: #0b3d91;
  color: white;
}

.hero {
  background: linear-gradient(#0b3d91, #1f6feb);
  color: white;
  padding: 48px 24px;
}
```

Meaning:
1. solid background for top header bar
2. `linear-gradient(topColor, bottomColor)` blends two blues for hero banner
3. `color: white` makes text readable on dark blue
4. larger padding makes banner feel spacious

## Finance example
SC website top navigation bar + welcome hero section.

---

# Topic 20. Flexbox for header layout (simple enough for assignment)

## Simple explanation
Your header needs brand on left and menu on right.

```css
.top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 24px;
}
```

Meaning:
1. `display: flex` — children share one horizontal row
2. `justify-content: space-between` — first item left, last item right
3. `align-items: center` — vertically center brand and links
4. padding gives inner spacing in header

HTML idea:

```html
<header class="top">
  <div class="brand">SC Bank</div>
  <nav>
    <a href="#home">Home</a>
    <a href="form.html">Register</a>
  </nav>
</header>
```

## Industry best practice
For this assignment, basic flexbox for header is enough.  
You do not need advanced grid layouts yet.

---

# Topic 21. Text decoration, cursor, links

```css
nav a {
  color: white;
  margin-left: 14px;
  text-decoration: none;
}

.btn {
  text-decoration: none;
  cursor: pointer;
}
```

Meaning:
1. `text-decoration: none` removes underline from links
2. `margin-left` separates menu links
3. `cursor: pointer` shows hand cursor on buttons/links

## Finance example
Top nav links on dark blue header without underlines look cleaner and more “bank portal”.

---

# Topic 22. Position property (classroom topic, keep simple)

## Simple explanation
Position changes how an element is placed.

1. `static` — default normal flow
2. `relative` — move a little from normal place
3. `absolute` — place relative to positioned ancestor
4. `fixed` — stick to screen while scrolling

## Beginner syntax

```css
.badge {
  position: relative;
  top: 4px; /* move down 4px from where it normally is */
}

.help-icon {
  position: fixed;
  right: 16px;
  bottom: 16px;
}
```

## Finance example
A fixed “Need help?” button can stay visible while customer scrolls a long loan form.

## Industry best practice for freshers
1. First finish form layout with normal flow + margin/padding.
2. Use position only when needed.
3. Overusing absolute/fixed makes pages break on different screen sizes.

---

# Topic 23. Combinators (classroom topic explained slowly)

## Simple explanation
Combinators style elements based on relationship to other elements.

### 1) Descendant combinator = space

```css
form input {
  border: 1px solid #c9d4e3;
}
```

Meaning: any `input` inside any `form` (even nested).

### 2) Child combinator `>`

```css
form > label {
  font-weight: bold;
}
```

Meaning: only labels that are **direct children** of form.

### 3) Adjacent sibling `+`

```css
label + input {
  margin-top: 4px;
}
```

Meaning: input that comes immediately after a label.

### 4) General sibling `~`

```css
label ~ span.error {
  color: #b00020;
}
```

Meaning: error span that is a sibling after label (not necessarily immediately after).

## Finance example
Style only inputs inside account form, not search box in header.

## Industry best practice
1. Keep selectors short.
2. Prefer `.account-form input` class style over deep chains.
3. Avoid `div div div span input`.

---

# Topic 24. Read the assignment CSS in English

This is from `Assignments\Week3\02-html-css\styles.css`.  
If you understand this file, you can finish the styling part.

```css
* { box-sizing: border-box; font-family: Arial, Helvetica, sans-serif; }
```
1. style everything
2. easier width calculation
3. common readable font

```css
body { margin: 0; background: #f4f7fb; color: #12263a; }
```
1. remove default page margin
2. light page background
3. dark text color

```css
.top { background: #0b3d91; color: white; padding: 16px 24px; display: flex; justify-content: space-between; align-items: center; }
```
1. blue header bar
2. white text
3. flex layout with brand left / links right

```css
.card { background: white; margin: 20px auto; padding: 20px; width: 90%; max-width: 520px; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.08); }
```
1. centered white form/content card

```css
label { display: block; margin-top: 12px; margin-bottom: 4px; font-weight: bold; }
input { width: 100%; padding: 10px; border: 1px solid #c9d4e3; border-radius: 4px; }
```
1. label above field
2. full-width inputs with clear border

```css
button, .btn { display: inline-block; margin-top: 16px; margin-right: 8px; padding: 10px 16px; background: #0b3d91; color: white; border: none; border-radius: 4px; text-decoration: none; cursor: pointer; }
```
1. primary bank buttons/links

```css
button[type="reset"], .btn.secondary { background: #5b6b7c; }
```
1. attribute selector: button whose type is reset
2. secondary actions in gray

---

# Topic 25. Mini practice order for the HTML/CSS assignment

Do in this exact order:

1. Create HTML form fields and validation first (no CSS worry yet).
2. Create `welcome.html` with heading “Welcome User”.
3. Create `styles.css` and link it in both pages.
4. Style `body`, then `.card`, then `label`, then `input`, then `button`.
5. Build `sc-website.html` with header/hero/services using same CSS.
6. Test GET and POST on the form.

If stuck, change one CSS property at a time and refresh browser.

---

# End-to-end mini banking story

1. Build Open Account HTML page with semantic structure.
2. Add required fields and patterns for name/mobile/email.
3. Use method POST.
4. Style with external CSS in bank brand colors using selectors + box model.
5. On submit success, show clear confirmation page (“Welcome User”).
6. Backend (later with Java/JDBC) stores customer safely in PostgreSQL.

---

# Fresh hire checklist before you say “UI done”

1. Are all important fields labeled and required?
2. Is method POST for personal/financial data?
3. Are validation messages clear?
4. Is CSS external and readable?
5. Can you explain each CSS line in your `styles.css` in plain English?
6. Does the page still make sense on a small laptop screen?
7. Would you trust this page with your own salary account details?

---

# CSS cheat sheet (only what freshers need for this assignment)

| Syntax | Meaning |
|--------|---------|
| `selector { property: value; }` | basic CSS rule |
| `*` | all elements |
| `p` / `label` / `input` | element selector |
| `.card` | class selector |
| `#openAccountForm` | id selector |
| `a, button` | group selector |
| `color` | text color |
| `background` | background fill |
| `font-size` | text size |
| `font-weight: bold` | bold text |
| `padding` | inner space |
| `margin` | outer space |
| `border` | outline |
| `border-radius` | rounded corners |
| `width` / `max-width` | sizing |
| `display: block` | one per line, full width style |
| `display: inline-block` | in a row but sizeable |
| `display: flex` | easy horizontal layout |
| `text-decoration: none` | remove link underline |
| `cursor: pointer` | hand cursor |
