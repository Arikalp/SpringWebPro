import React from "react";
import "./Footer.css";

const Footer = () => {
  const currentYear = new Date().getFullYear();

  return (
    <footer className="footer">
      <div className="footer-container">
        <div className="footer-section">
          <h3 className="footer-brand">UBUY</h3>
          <p className="footer-description">
            Your one-stop destination for quality products at the best prices.
          </p>
          <div className="social-links">
            <a href="#" className="social-link" aria-label="Facebook">
              <i className="bi bi-facebook"></i>
            </a>
            <a href="#" className="social-link" aria-label="Twitter">
              <i className="bi bi-twitter"></i>
            </a>
            <a href="#" className="social-link" aria-label="Instagram">
              <i className="bi bi-instagram"></i>
            </a>
            <a href="#" className="social-link" aria-label="LinkedIn">
              <i className="bi bi-linkedin"></i>
            </a>
          </div>
        </div>

        <div className="footer-section">
          <h4 className="footer-title">Quick Links</h4>
          <ul className="footer-links">
            <li><a href="/">Home</a></li>
            <li><a href="/add_product">Add Product</a></li>
            <li><a href="#about">About Us</a></li>
            <li><a href="#contact">Contact</a></li>
          </ul>
        </div>

        <div className="footer-section">
          <h4 className="footer-title">Customer Service</h4>
          <ul className="footer-links">
            <li><a href="#help">Help Center</a></li>
            <li><a href="#returns">Returns</a></li>
            <li><a href="#shipping">Shipping Info</a></li>
            <li><a href="#track">Track Order</a></li>
          </ul>
        </div>

        <div className="footer-section">
          <h4 className="footer-title">Newsletter</h4>
          <p className="footer-newsletter-text">
            Subscribe to get special offers and updates.
          </p>
          <div className="newsletter-form">
            <input
              type="email"
              placeholder="Enter your email"
              className="newsletter-input"
            />
            <button className="newsletter-btn">
              <i className="bi bi-send"></i>
            </button>
          </div>
        </div>
      </div>

      <div className="footer-bottom">
        <p className="footer-copyright">
          © {currentYear} UBUY. All rights reserved.
        </p>
        <p className="footer-developed">
          Developed with <i className="bi bi-heart-fill"></i> by Sankalp
        </p>
        <div className="footer-legal">
          <a href="#privacy">Privacy Policy</a>
          <span className="divider">•</span>
          <a href="#terms">Terms of Service</a>
          <span className="divider">•</span>
          <a href="#cookies">Cookie Policy</a>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
