# Dynamic Flui-X 🌌

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![React](https://img.shields.io/badge/React-19-61dafb.svg)
![TypeScript](https://img.shields.io/badge/TypeScript-5.0-blue.svg)
![Tailwind](https://img.shields.io/badge/Tailwind_CSS-3.0-38bdf8.svg)

**Dynamic Flui-X** is a high-fidelity, interactive physics simulation application built for the web. It combines rigid body physics, particle systems, and fluid dynamics into a single, visually stunning experience using the HTML5 Canvas API and React.

Whether you want to smash geometric shapes together, watch thousands of particles flow, or stir up neon-colored fluids, Dynamic Flui-X provides a performant and customizable playground.

## ✨ Features

### 1. Shape Simulation (Rigid Body Physics)
*   **Polygon Collisions:** Simulate circles, triangles, squares, hexagons, and octagons with real-time collision resolution.
*   **Physics Engine:** Custom grid-based spatial partitioning for performance. Control gravity (X/Y), air resistance, friction, and restitution (bounciness).
*   **Interactivity:** Drag objects, create vortexes, or repel shapes with mouse/touch forces.
*   **Tilt Control:** Use your device's gyroscope (mobile) to control gravity.

### 2. Particle Simulation
*   **High Performance:** Renders thousands of particles using **Object Pooling** to minimize garbage collection overhead.
*   **Emitters:** Customizable emission rates, life span, and initial velocity.
*   **Render Modes:** Switch between Dots, Lines (velocity-based), and Squares.
*   **Behaviors:** Create fountains, fire, snow, or galactic swirls.

### 3. Fluid Simulation
*   **Grid-Based Dynamics:** A real-time implementation of fluid mechanics (solving for advection, diffusion, and projection).
*   **Visuals:** Beautiful dye injection logic with support for Rainbow Flow, Velocity-based coloring, or Uniform colors.
*   **Customization:** Tweak viscosity, diffusion rates, dissipation (fading), and solver iterations for accuracy vs. performance.

### 🌟 General Features
*   **Custom Presets:** Save and load your own physics configurations to Local Storage (Slots C1-C5).
*   **Neon Aesthetics:** "Glow" effects using additive blending global composite operations.
*   **Multi-Language Support:** Fully localized in English, Turkish, Russian, Japanese, and German.
*   **Responsive Design:** Works seamlessly on Desktop and Mobile (touch optimized).

## 🛠️ Tech Stack

*   **Frontend Library:** [React 19](https://react.dev/)
*   **Language:** [TypeScript](https://www.typescriptlang.org/)
*   **Styling:** [Tailwind CSS](https://tailwindcss.com/)
*   **Rendering:** HTML5 Canvas API (2D Context)
*   **Icons:** Heroicons

## 🚀 Getting Started

To run this project locally, follow these steps:

### Prerequisites
*   Node.js (v16 or higher)
*   npm or yarn

### Installation

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/dynamic-fluix.git
    cd dynamic-fluix
    ```

2.  **Install dependencies:**
    ```bash
    npm install
    # or
    yarn install
    ```

3.  **Start the development server:**
    ```bash
    npm start
    # or
    yarn dev
    ```

4.  Open your browser and navigate to `http://localhost:3000` (or the port shown in your terminal).

## 🎮 Controls

*   **Menu:** Click the Hamburger icon (top-right) to open the Settings panel.
*   **Interaction:**
    *   **Mouse/Touch:** Stir fluids, attract/repel particles, or disrupt shapes.
    *   **Long Press (Presets):** Hold a Custom Preset button (C1-C5) for 1.2 seconds to save your current configuration. Tap to load.
*   **Device Orientation:** Enable "Tilt Sensor" in the Settings (Shape Sim) to control gravity by tilting your phone/tablet.

## 📷 Screenshots

*(You can add screenshots here later)*

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1.  Fork the project
2.  Create your feature branch (`git checkout -b feature/AmazingFeature`)
3.  Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4.  Push to the branch (`git push origin feature/AmazingFeature`)
5.  Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

<p align="center">
  Built with ❤️ by The Log Space
</p>
