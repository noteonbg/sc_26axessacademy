# Step-by-Step Progressive Building Guide for Standard Chartered Landing Page

This folder (`\poc\`) contains the **step-by-step evolutionary breakdown** of building the landing page design from scratch across 6 distinct stages.

Each stage provides a standalone HTML file and a standalone CSS file so you can inspect exactly how the HTML structure and CSS styles develop from a blank canvas to the final completed page.

---

## Stage Breakdown Overview

### **Stage 1: HTML Boilerplate & Page Skeleton**
* **HTML File**: [`stage1_skeleton.html`](/poc/stage1_skeleton.html)
* **CSS File**: [`stage1_style.css`](poc/stage1_style.css)
* **What we build**:
  - `<!DOCTYPE html>`, `<head>`, `<meta charset="UTF-8">`, viewport settings.
  - Global CSS Reset (`* { box-sizing: border-box; margin: 0; padding: 0; }`).
  - Base document typography and light grey body background (`#f4f6f8`).
  - Main semantic layout containers (`<header>`, `<main>`, `<section>`).

---

### **Stage 2: Sticky Navigation Header**
* **HTML File**: [`stage2_navbar.html`](/poc/stage2_navbar.html)
* **CSS File**: [`stage2_style.css`](/poc/poc/stage2_style.css)
* **What we build**:
  - Top header elements: Home icon, location picker (`You're in India ▾`), menu links (`Our Products`, `Promotions`, `Services`, `Help`), search button, green `LOGIN` button, and Standard Chartered logo.
  - Flexbox 1D alignment (`display: flex; justify-content: space-between; align-items: center;`).
  - Sticky header position (`position: sticky; top: 0; z-index: 1000`).

---

### **Stage 3: Hero Banner Section & Dual Overlay Gradient**
* **HTML File**: [`stage3_hero.html`](/poc/stage3_hero.html)
* **CSS File**: [`stage3_style.css`](/poc/poc/stage3_style.css)
* **What we build**:
  - `<section class="hero-section">` with headline `<h1>Welcome to Standard Chartered</h1>`.
  - Background image with 45% dark SC Blue overlay gradient (`linear-gradient(rgba(15, 59, 125, 0.45), rgba(15, 59, 125, 0.45)), url(...)`).
  - Headline formatting (`color: #ffffff; text-align: center; font-size: 2.75rem; font-weight: 300`).

---

### **Stage 4: 5-Column Cards Grid Layout**
* **HTML File**: [`stage4_grid.html`]poc/stage4_grid.html)
* **CSS File**: [`stage4_style.css`](poc/stage4_style.css)
* **What we build**:
  - `<div class="card-grid">` grid layout.
  - CSS Grid 2D layout rules (`display: grid; grid-template-columns: repeat(5, 1fr); gap: 18px`).
  - Constraining grid max-width (`max-width: 1400px; margin: 0 auto`).

---

### **Stage 5: Card Components & Image Fitting (`object-fit: cover`)**
* **HTML File**: [`stage5_cards.html`](poc/stage5_cards.html)
* **CSS File**: [`stage5_style.css`](poc/stage5_style.css)
* **What we build**:
  - Replacing placeholders with 5 `<article class="card">` items containing `<div class="card-media"><img ...></div>` and `<div class="card-body"><h3>...</h3><p>...</p></div>`.
  - Card background (`#ffffff`), rounded corners (`border-radius: 4px`), overflow clipping (`overflow: hidden`), and drop shadow (`box-shadow: 0 4px 15px rgba(0,0,0,0.18)`).
  - Photo cropping without stretching (`object-fit: cover`).

---

### **Stage 6: Final Feature Polish - Hover Animations & Responsiveness**
* **HTML File**: [`stage6_final.html`](poc/stage6_final.html)
* **CSS File**: [`stage6_style.css`](poc/stage6_final.html)
* **What we build**:
  - Tactile card hover float micro-animation (`transform: translateY(-6px)`, `box-shadow: 0 12px 25px rgba(...)`).
  - Photo zoom micro-animation on hover (`transform: scale(1.04)`).
  - Responsive media queries (`@media (max-width: 1200px)` for 3-column tablet view and `@media (max-width: 768px)` for 1-column mobile view).

