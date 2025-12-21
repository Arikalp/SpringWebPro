import React, { useState } from "react";
import axios from "axios";
import "./AddProduct.css";

const AddProduct = () => {
  const [product, setProduct] = useState({
    prodName: "",
    prodPrice: "",
    description: "",
    brand: "",
    category: "",
    imageUrl: ""
  });
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [message, setMessage] = useState("");

  const handleChange = (e) => {
    setProduct({
      ...product,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);
    setMessage("");

    try {
      await axios.post("http://localhost:8080/api/addProducts", product);
      setMessage("Product added successfully!");
      setProduct({
        prodName: "",
        prodPrice: "",
        description: "",
        brand: "",
        category: "",
        imageUrl: ""
      });
    } catch (error) {
      setMessage("Error adding product. Please try again.");
      console.error("Error:", error);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="add-product-container">
      <div className="add-product-header">
        <h2>Add New Product</h2>
        <p className="text-muted">Fill in the details below to add a new product to your inventory</p>
      </div>
      <div className="add-product-content">
        <div className="add-product-form-section">
          <div className="card shadow-sm">
            <div className="card-body">
              {message && (
                <div className={`alert ${message.includes('successfully') ? 'alert-success' : 'alert-danger'}`}>
                  {message}
                </div>
              )}
              <form onSubmit={handleSubmit}>
                <div className="form-row">
                  <div className="form-group-half">
                    <label className="form-label">Product Name</label>
                    <input
                      type="text"
                      className="form-control"
                      name="prodName"
                      value={product.prodName}
                      onChange={handleChange}
                      required
                    />
                  </div>
                  <div className="form-group-half">
                    <label className="form-label">Price</label>
                    <input
                      type="number"
                      step="0.01"
                      className="form-control"
                      name="prodPrice"
                      value={product.prodPrice}
                      onChange={handleChange}
                      required
                    />
                  </div>
                </div>
                <div className="form-row">
                  <div className="form-group-half">
                    <label className="form-label">Brand</label>
                    <input
                      type="text"
                      className="form-control"
                      name="brand"
                      value={product.brand}
                      onChange={handleChange}
                      required
                    />
                  </div>
                  <div className="form-group-half">
                    <label className="form-label">Category</label>
                    <input
                      type="text"
                      className="form-control"
                      name="category"
                      value={product.category}
                      onChange={handleChange}
                      required
                    />
                  </div>
                </div>
                <div className="mb-3">
                  <label className="form-label">Description</label>
                  <textarea
                    className="form-control"
                    name="description"
                    rows="3"
                    value={product.description}
                    onChange={handleChange}
                  ></textarea>
                </div>
                <div className="mb-3">
                  <label className="form-label">Image URL</label>
                  <input
                    type="url"
                    className="form-control"
                    name="imageUrl"
                    value={product.imageUrl}
                    onChange={handleChange}
                    placeholder="https://example.com/image.jpg"
                  />
                </div>
                <div className="form-actions">
                  <button
                    type="submit"
                    className="btn btn-primary btn-submit"
                    disabled={isSubmitting}
                  >
                    {isSubmitting ? "Adding..." : "Add Product"}
                  </button>
                  <button
                    type="button"
                    className="btn btn-secondary btn-reset"
                    onClick={() => setProduct({
                      prodName: "",
                      prodPrice: "",
                      description: "",
                      brand: "",
                      category: "",
                      imageUrl: ""
                    })}
                  >
                    Reset Form
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
        <div className="add-product-info-section">
          <div className="info-card">
            <h4>Product Guidelines</h4>
            <ul>
              <li>Use clear and descriptive product names</li>
              <li>Ensure prices are accurate and up-to-date</li>
              <li>Provide detailed descriptions for better customer understanding</li>
              <li>Use high-quality image URLs for product images</li>
              <li>Double-check all information before submitting</li>
            </ul>
          </div>
          {product.imageUrl && (
            <div className="info-card image-preview">
              <h4>Image Preview</h4>
              <img src={product.imageUrl} alt="Product preview" onError={(e) => {e.target.style.display = 'none'}} />
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default AddProduct;