# CSS Organization Structure

This project now uses a modular CSS architecture for better maintainability and readability.

## File Structure

```
client/src/
├── styles/
│   ├── variables.css      # Theme variables (light/dark mode)
│   └── global.css         # Global styles (reset, typography, scrollbar, etc.)
├── components/
│   ├── Navbar.css         # Navbar component styles
│   ├── Home.css           # Home page and product grid styles
│   ├── AddProduct.css     # Add Product page styles
│   ├── ProductDetails.css # Product details page styles
│   └── Footer.css         # Footer component styles
└── App.css                # Shared component styles (cards, buttons, etc.)
```

## Import Order

In `App.jsx`, CSS files are imported in this order:
1. `variables.css` - Theme variables must load first
2. `global.css` - Global styles and resets
3. `App.css` - Shared component styles

Each component imports its own CSS file:
```jsx
import "./ComponentName.css";
```

## CSS Files Overview

### `styles/variables.css`
- Light and dark theme color schemes
- CSS custom properties (variables)
- Design tokens (shadows, border-radius, etc.)

### `styles/global.css`
- CSS reset and box-sizing
- Base typography styles
- Custom scrollbar styling
- Selection highlighting
- Utility classes

### `components/Navbar.css`
- Navbar layout and styling
- Search input
- Theme toggle button
- Dropdown menus
- Mobile responsive styles

### `components/Home.css`
- Product grid layout
- Modern product card design
- Hover animations
- Add to cart button
- Mobile responsive grid

### `components/AddProduct.css`
- Form layout (2-column grid)
- Input field styling
- Form validation styles
- Info panel
- Image preview
- Mobile responsive forms

### `components/ProductDetails.css`
- Product detail layout
- Image gallery
- Product description
- Configuration options
- Add to cart functionality
- Mobile responsive layout

### `components/Footer.css`
- Footer grid layout
- Newsletter subscription
- Social media links
- Legal links
- Mobile responsive footer

### `App.css`
- Shared card component styles
- Shared button styles
- Legacy utility classes

## Benefits of This Structure

1. **Modularity**: Each component has its own CSS file
2. **Maintainability**: Easy to find and modify styles
3. **Performance**: Only load styles that are actually used
4. **Readability**: Cleaner, smaller files are easier to understand
5. **Collaboration**: Multiple developers can work on different components
6. **Scalability**: Easy to add new components without bloating a single file

## Adding New Components

When creating a new component:

1. Create a new CSS file in `components/` folder
2. Import it in your component file
3. Use the existing CSS variables for consistency

Example:
```jsx
// NewComponent.jsx
import React from "react";
import "./NewComponent.css";

const NewComponent = () => {
  // component code
};
```

```css
/* NewComponent.css */
.new-component {
  background-color: var(--card-bg-clr);
  border-radius: var(--border-radius);
  box-shadow: var(--shadow-md);
}
```

## Theme Variables

All colors and design tokens are defined in `variables.css`. Use these variables instead of hard-coded values:

- `--accent-color`: Primary brand color
- `--card-bg-clr`: Card background
- `--para-clr`: Text color
- `--shadow-sm/md/lg`: Box shadows
- `--border-radius`: Border radius
- etc.

This ensures consistency and makes theme switching seamless.
