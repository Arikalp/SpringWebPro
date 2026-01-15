import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./Home.css";

const Home = ({ searchResults }) => {
  const [products, setProducts] = useState([]);
  const [isError, setIsError] = useState(false);
  const navigate = useNavigate();

  const displayProducts = searchResults.length > 0 ? searchResults : products;

  const handleProductClick = (productId) => {
    navigate(`/product/${productId}`);
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/products");
        setProducts(response.data);
        console.log(response.data);
      } catch (error) {
        console.error("Error fetching data:", error);
        setIsError(true);
      }
    };

    fetchData();
  }, []);

  if (isError) {
    return (
      <h2 className="text-center" style={{ padding: "10rem" }}>
        Something went wrong...
      </h2>
    );
  }

  return (
    <>
      <div className="grid">
        {displayProducts.map((product) => (
          <div
            className="product-card-modern"
            key={product.prodId}
            onClick={() => handleProductClick(product.prodId)}
          >
            <div className="product-image-container">
              <img
                src={product.imageUrl || "https://via.placeholder.com/300x200?text=No+Image"}
                alt={product.prodName}
                className="product-image"
              />
            </div>
            <div className="product-content">
              <div className="product-header">
                <h5 className="product-title">{product.prodName}</h5>
                <span className="product-brand">{product.brand}</span>
              </div>
              <div className="product-footer">
                <h5 className="product-price">
                  <i className="bi bi-currency-rupee"></i>
                  {product.prodPrice}
                </h5>
                <button className="btn-add-cart" onClick={(e) => { e.stopPropagation(); }}>
                  <i className="bi bi-cart-plus"></i> Add
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </>
  );
};

export default Home;
