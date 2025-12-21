import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import "./ProductDetails.css";

const ProductDetails = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(false);

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/products/${id}`);
        setProduct(response.data);
      } catch (error) {
        console.error("Error fetching product:", error);
        setError(true);
      } finally {
        setLoading(false);
      }
    };

    fetchProduct();
  }, [id]);

  if (loading) {
    return (
      <div className="container mt-4">
        <div className="text-center">
          <div className="spinner-border" role="status">
            <span className="visually-hidden">Loading...</span>
          </div>
        </div>
      </div>
    );
  }

  if (error || !product) {
    return (
      <div className="container mt-4">
        <div className="alert alert-danger text-center">
          Product not found or error loading product details.
        </div>
      </div>
    );
  }

  return (
    <div className="container mt-4">
      <button 
        className="btn btn-outline-secondary mb-3"
        onClick={() => navigate(-1)}
      >
        ← Back
      </button>
      
      <div className="row">
        <div className="col-md-6">
          <div className="product-image-container">
            <img
              src={product.imageUrl || "https://via.placeholder.com/500x400?text=No+Image"}
              alt={product.prodName}
              className="img-fluid rounded shadow"
              style={{ width: "100%", maxHeight: "400px", objectFit: "cover" }}
            />
          </div>
        </div>
        
        <div className="col-md-6">
          <div className="product-details">
            <h1 className="product-title">{product.prodName}</h1>
            <p className="text-muted mb-2">by {product.brand}</p>
            
            <div className="price-section mb-3">
              <h2 className="text-success mb-0">
                <i className="bi bi-currency-rupee"></i>{product.prodPrice}
              </h2>
            </div>
            
            <div className="product-info mb-4">
              <div className="info-item mb-2">
                <strong>Category:</strong> {product.category}
              </div>
              <div className="info-item mb-2">
                <strong>Brand:</strong> {product.brand}
              </div>
              {product.releaseDate && (
                <div className="info-item mb-2">
                  <strong>Release Date:</strong> {new Date(product.releaseDate).toLocaleDateString()}
                </div>
              )}
            </div>
            
            {product.description && (
              <div className="description-section mb-4">
                <h4>Description</h4>
                <p className="text-muted">{product.description}</p>
              </div>
            )}
            
            <div className="action-buttons">
              <button className="btn btn-primary btn-lg me-3">
                <i className="bi bi-cart-plus"></i> Add to Cart
              </button>
              <button className="btn btn-outline-danger btn-lg">
                <i className="bi bi-heart"></i> Wishlist
              </button>
            </div>
          </div>
        </div>
      </div>
      
      <div className="row mt-5">
        <div className="col-12">
          <div className="card">
            <div className="card-header">
              <h4>Product Specifications</h4>
            </div>
            <div className="card-body">
              <div className="row">
                <div className="col-md-6">
                  <table className="table table-borderless">
                    <tbody>
                      <tr>
                        <td><strong>Product ID:</strong></td>
                        <td>{product.prodId}</td>
                      </tr>
                      <tr>
                        <td><strong>Brand:</strong></td>
                        <td>{product.brand}</td>
                      </tr>
                      <tr>
                        <td><strong>Category:</strong></td>
                        <td>{product.category}</td>
                      </tr>
                    </tbody>
                  </table>
                </div>
                <div className="col-md-6">
                  <table className="table table-borderless">
                    <tbody>
                      <tr>
                        <td><strong>Price:</strong></td>
                        <td>₹{product.prodPrice}</td>
                      </tr>
                      {product.releaseDate && (
                        <tr>
                          <td><strong>Release Date:</strong></td>
                          <td>{new Date(product.releaseDate).toLocaleDateString()}</td>
                        </tr>
                      )}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductDetails;