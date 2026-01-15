import React, { useEffect, useState } from "react";
import axios from "axios";
import "./Navbar.css";

const Navbar = ({ onSearch }) => {
  const [searchTerm, setSearchTerm] = useState("");
  const handleSearch = async (e) => {
    const value = e.target.value;
    setSearchTerm(value);
    
    if (value.trim() === "") {
      onSearch([]);
      return;
    }

    try {
      const response = await axios.get(`http://localhost:8080/api/products/search?keyword=${value}`);
      onSearch(response.data);
    } catch (error) {
      console.error("Error searching products:", error);
      onSearch([]);
    }
  };

  const getInitialTheme = () => {
    const storedTheme = localStorage.getItem("theme");
    return storedTheme ? storedTheme : "light-theme";
  };

  const [theme, setTheme] = useState(getInitialTheme());

  const toggleTheme = () => {
    const newTheme = theme === "dark-theme" ? "light-theme" : "dark-theme";
    setTheme(newTheme);
    localStorage.setItem("theme", newTheme);
  };

  useEffect(() => {
    document.body.className = theme;
  }, [theme]);

  return (
    <>
      <header>
        <nav className="navbar navbar-expand-lg fixed-top">
          <div className="container-fluid">
            <a className="navbar-brand" href="/">
              UBUY
            </a>
            <button
              className="navbar-toggler"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#navbarSupportedContent"
              aria-controls="navbarSupportedContent"
              aria-expanded="false"
              aria-label="Toggle navigation"
            >
              <span className="navbar-toggler-icon"></span>
            </button>
            <div
              className="collapse navbar-collapse"
              id="navbarSupportedContent"
            >
              <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                <li className="nav-item">
                  <a className="nav-link active" aria-current="page" href="/">
                    Home
                  </a>
                </li>
                <li className="nav-item">
                  <a className="nav-link" href="/add_product">
                    Add Product
                  </a>
                </li>

                {/* < className="nav-item dropdown"> */}
                <a
                  className="nav-link dropdown-toggle"
                  href="/"
                  role="button"
                  data-bs-toggle="dropdown"
                  aria-expanded="false"
                >
                  Categories
                </a>

                <li className="nav-item"></li>
              </ul>
              <button className="theme-btn" onClick={() => toggleTheme()}>
                {theme === "dark-theme" ? (
                  <i className="bi bi-moon-fill"></i>
                ) : (
                  <i className="bi bi-sun-fill"></i>
                )}
              </button>
              <div className="d-flex align-items-center cart gap-3">
                <input
                  className="search-input"
                  type="search"
                  placeholder="Search products..."
                  aria-label="Search"
                  value={searchTerm}
                  onChange={handleSearch}
                />
                <a href="/cart" className="cart-link">
                  <i className="bi bi-cart3" style={{ fontSize: "1.25rem" }}></i>
                </a>
              </div>
            </div>
          </div>
        </nav>
      </header>
    </>
  );
};

export default Navbar;
