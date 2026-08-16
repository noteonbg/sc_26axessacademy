# Developer Guide: How to Use CSS Across a Multi-Component React Website

This guide provides simple, decimal-numbered step-by-step instructions for structuring and using CSS across multiple React components, using **`06_react_css_styling_architecture`** as a standalone reference.

---

## 1. Overview of the 4 React CSS Styling Strategies

1.1 Strategy 1: Global CSS (`src/styles/global.css`)  
Defines global CSS variables (`:root`), global layout containers, reset rules, and base typography shared across all components in the website.

1.2 Strategy 2: Component-Specific CSS (`Header.css`)  
Imports dedicated stylesheets directly into individual component files for layout styling.

1.3 Strategy 3: Scoped CSS Modules (`ProductCard.module.css`)  
Scopes CSS class names locally to prevent global class name collisions between different components created by different developers.

1.4 Strategy 4: Dynamic Inline Styles (`style={{ backgroundColor: color }}`)  
Calculates dynamic style overrides inside JavaScript based on changing component state and props.

---

## 2. Directory Structure Blueprint

2.1 File and Folder Layout:
```
src/
├── styles/
│   └── global.css                   # Strategy 1: Global CSS & Variables
├── components/
│   ├── Header/
│   │   ├── Header.jsx               # Strategy 2: Component-Specific CSS
│   │   └── Header.css
│   ├── ProductCard/
│   │   ├── ProductCard.jsx          # Strategy 3: Scoped CSS Modules (.module.css)
│   │   └── ProductCard.module.css
│   └── DynamicBadge/
│       └── DynamicBadge.jsx         # Strategy 4: Dynamic Inline Styles
├── App.jsx                          # Root Component Container
└── index.js                         # React DOM Entrypoint
```

---

## 3. What to Put in Global CSS vs Component-Specific CSS (with Examples)

3.1 What Belongs in Global CSS (`src/styles/global.css`):

3.1.1 CSS Custom Properties / Design Tokens (`:root`):  
Stores brand colors, theme background values, default fonts, and border radiuses so all components share the same design language:
```css
:root {
    --primary-color: #2563eb;
    --primary-dark: #1d4ed8;
    --dark-bg: #0f172a;
    --font-sans: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    --border-radius: 8px;
}
```

3.1.2 CSS Resets & Base Typography:  
Removes browser default margins and sets base typography across standard HTML tags (`body`, `h1`, `h2`, `p`, `a`):
```css
* {
    box-sizing: border-box;
}

body {
    font-family: var(--font-sans);
    background-color: #f8fafc;
    color: #334155;
    margin: 0;
    padding: 0;
}
```

3.1.3 Page-Level Layout Containers:  
Defines global page wrapper widths shared by all web pages:
```css
.global-container {
    max-width: 1000px;
    margin: 0 auto;
    padding: 30px 20px;
}
```

3.1.4 Shared Utility Classes:  
Reusable helper classes applied across any component:
```css
.text-center { text-align: center; }
.flex-between { display: flex; justify-content: space-between; align-items: center; }
.mt-20 { margin-top: 20px; }
```

---

3.2 What Belongs in Component-Specific CSS (`Header.css` or `ProductCard.module.css`):

3.2.1 Component Container & Inner Sub-Element Layout:  
Styles specific only to that component's structural layout and internal elements:
```css
.app-header {
    background-color: var(--dark-bg);
    color: white;
    padding: 15px 40px;
}

.header-brand {
    font-size: 1.3rem;
    font-weight: 700;
}
```

3.2.2 Component Hover, Active & Focus States:  
Styles defining interactive states for component buttons or cards:
```css
.card {
    background-color: white;
    border: 1px solid #e2e8f0;
    transition: transform 0.2s, box-shadow 0.2s;
}

.card:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 16px rgba(0, 0, 0, 0.1);
}
```

3.2.3 Component-Specific Animations & Keyframes:  
Keyframe micro-animations used solely inside a specific component:
```css
@keyframes pulseCard {
    0% { transform: scale(1); }
    50% { transform: scale(1.02); }
    100% { transform: scale(1); }
}

.animated-card {
    animation: pulseCard 2s infinite;
}
```

---

## 4. Step-by-Step Code Implementations with Syntax Explanations

### 4.1 Strategy 1 Implementation: Global CSS Variables (`src/styles/global.css`)

4.1.1 Step 1: Create Global CSS File  
Create `src/styles/global.css` and define CSS custom variables:
```css
:root {
    --primary-color: #2563eb;
    --dark-bg: #0f172a;
    --font-family: 'Segoe UI', sans-serif;
}

body {
    font-family: var(--font-family);
    margin: 0;
}

.global-container {
    max-width: 1000px;
    margin: 0 auto;
    padding: 30px;
}
```

4.1.2 Step 2: Import Once in `src/index.js`  
Import `global.css` at the top of `src/index.js` so CSS variables are available across the entire site:
```javascript
import React from 'react';
import ReactDOM from 'react-dom/client';
import './styles/global.css'; // Global CSS import
import App from './App';
```

---

### 4.2 Strategy 2 Implementation: Component-Specific CSS (`Header.jsx` & `Header.css`)

4.2.1 Step 1: Create CSS File for the Component  
Create `Header.css` in the component's folder:
```css
.app-header {
    background-color: var(--dark-bg);
    color: white;
    padding: 15px 40px;
    display: flex;
    justify-content: space-between;
}
```

4.2.2 Step 2: Import CSS directly in `Header.jsx`  
```javascript
import React from 'react';
import './Header.css'; // Direct component CSS import

export default function Header() {
    return (
        <header className="app-header">
            <div className="header-brand">Enterprise React Portal</div>
        </header>
    );
}
```

---

### 4.3 Strategy 3 Implementation: Scoped CSS Modules (`ProductCard.jsx` & `ProductCard.module.css`)

4.3.1 Step 1: Create Module File with `.module.css` Extension  
Name the file `ProductCard.module.css`:
```css
.card {
    background-color: white;
    padding: 20px;
    border-radius: 8px;
}

.button {
    background-color: var(--primary-color);
    color: white;
}
```

4.3.2 Step 2: Import Scoped Object in `ProductCard.jsx`  
Import styles as an object named `styles` and access class names via `styles.className`:
```javascript
import React from 'react';
import styles from './ProductCard.module.css'; // Scoped CSS module import

export default function ProductCard({ title }) {
    return (
        <div className={styles.card}>
            <h3>{title}</h3>
            <button className={styles.button}>View Item</button>
        </div>
    );
}
```

4.3.3 Syntax Explanation for CSS Modules  
Build tools automatically transform class names to unique hashes (e.g. `ProductCard_card__3xK12`), ensuring zero CSS style collisions between components.

---

### 4.4 Strategy 4 Implementation: Dynamic Inline Styles (`DynamicBadge.jsx`)

4.4.1 Step 1: Define Style Calculation Function  
Create a JavaScript object with CSS properties inside your component:
```javascript
import React, { useState } from 'react';

export default function DynamicBadge({ status }) {
    const [isHovered, setIsHovered] = useState(false);

    const badgeStyle = {
        backgroundColor: status === 'ACTIVE' ? (isHovered ? '#15803d' : '#dcfce7') : '#fee2e2',
        color: status === 'ACTIVE' ? (isHovered ? '#ffffff' : '#15803d') : '#b91c1c',
        padding: '4px 12px',
        borderRadius: '12px'
    };

    return (
        <span 
            style={badgeStyle}
            onMouseEnter={() => setIsHovered(true)}
            onMouseLeave={() => setIsHovered(false)}
        >
            Status: {status}
        </span>
    );
}
```

4.4.2 Syntax Explanation for Dynamic Inline Styles  
`style={badgeStyle}` passes a JavaScript object to React's `style` attribute. Property names use camelCase (e.g., `backgroundColor` instead of `background-color`).

---

## 5. How to Run the Project

5.1 Step 1: Open Terminal in Directory  
```bash
cd 06_react_css_styling_architecture
```

5.2 Step 2: Install Dependencies  
```bash
npm install
```

5.3 Step 3: Start Dev Server  
```bash
npm start
```
Open `http://localhost:3000/` in your browser to test all 4 CSS strategies live.
