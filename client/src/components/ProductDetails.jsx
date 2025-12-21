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
  const [selectedImage, setSelectedImage] = useState(0);
  const [quantity, setQuantity] = useState(1);

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
      <div className="product-details-page">
        <div className="loading-container">
          <div className="spinner"></div>
          <p>Loading product details...</p>
        </div>
      </div>
    );
  }

  if (error || !product) {
    return (
      <div className="product-details-page">
        <div className="error-container">
          <i className="bi bi-exclamation-circle"></i>
          <h3>Product not found</h3>
          <p>Sorry, we couldn't find the product you're looking for.</p>
          <button className="btn-back" onClick={() => navigate(-1)}>
            Go Back
          </button>
        </div>
      </div>
    );
  }

  // For demo purposes, using the same image multiple times
  const productImages = [
    product.imageUrl || "https://via.placeholder.com/600x600?text=No+Image",
    product.imageUrl || "https://via.placeholder.com/600x600?text=No+Image",
    product.imageUrl || "https://via.placeholder.com/600x600?text=No+Image",
  ];

  const handleQuantityChange = (type) => {
    if (type === 'increment') {
      setQuantity(prev => prev + 1);
    } else if (type === 'decrement' && quantity > 1) {
      setQuantity(prev => prev - 1);
    }
  };

  return (
    <div className="product-details-page">
      {/* Breadcrumb Navigation */}
      <div className="breadcrumb-container">
        <div className="breadcrumb">
          <span onClick={() => navigate('/')} className="breadcrumb-link">Home</span>
          <i className="bi bi-chevron-right"></i>
          <span onClick={() => navigate('/')} className="breadcrumb-link">{product.category}</span>
          <i className="bi bi-chevron-right"></i>
          <span className="breadcrumb-current">{product.prodName}</span>
        </div>
      </div>

      {/* Main Product Section */}
      <div className="product-container">
        {/* Left: Image Gallery */}
        <div className="product-gallery">
          <div className="main-image-container">
            <img 
              src={productImages[selectedImage]} 
              alt={product.prodName}
              className="main-product-image"
            />
          </div>
          <div className="thumbnail-container">
            {productImages.map((img, index) => (
              <div 
                key={index}
                className={`thumbnail ${selectedImage === index ? 'active' : ''}`}
                onClick={() => setSelectedImage(index)}
              >
                <img src={img} alt={`${product.prodName} ${index + 1}`} />
              </div>
            ))}
          </div>
          
          {/* Action Buttons Below Image */}
          <div className="image-action-buttons">
            <button className="action-btn">
              <i className="bi bi-cart-plus"></i>
              ADD TO CART
            </button>
            <button className="action-btn secondary">
              <i className="bi bi-lightning-fill"></i>
              BUY NOW
            </button>
          </div>
        </div>

        {/* Right: Product Details */}
        <div className="product-info-section">
          {/* Product Title & Brand */}
          <div className="product-header">
            <h1 className="product-name">{product.prodName}</h1>
            <p className="product-brand">Brand: <span>{product.brand}</span></p>
          </div>

          {/* Rating Section */}
          <div className="rating-section">
            <div className="rating-badge">
              4.5 <i className="bi bi-star-fill"></i>
            </div>
            <span className="rating-count">2,345 Ratings & 456 Reviews</span>
          </div>

          {/* Price Section */}
          <div className="price-section">
            <div className="price-main">
              <i className="bi bi-currency-rupee"></i>{product.prodPrice}
            </div>
            <div className="price-original">
              <i className="bi bi-currency-rupee"></i>{(product.prodPrice * 1.3).toFixed(2)}
            </div>
            <div className="price-discount">23% off</div>
          </div>

          {/* Available Offers */}
          <div className="offers-section">
            <h3>Available Offers</h3>
            <div className="offer-item">
              <i className="bi bi-tag-fill"></i>
              <div>
                <strong>Bank Offer:</strong> 10% instant discount on SBI Credit Cards
              </div>
            </div>
            <div className="offer-item">
              <i className="bi bi-tag-fill"></i>
              <div>
                <strong>Special Price:</strong> Get extra 5% off (price inclusive of discount)
              </div>
            </div>
            <div className="offer-item">
              <i className="bi bi-tag-fill"></i>
              <div>
                <strong>Partner Offer:</strong> Sign-up for Pay Later & get free gift voucher
              </div>
            </div>
          </div>

          {/* Quantity Selector */}
          <div className="quantity-section">
            <h3>Quantity</h3>
            <div className="quantity-controls">
              <button onClick={() => handleQuantityChange('decrement')}>-</button>
              <span>{quantity}</span>
              <button onClick={() => handleQuantityChange('increment')}>+</button>
            </div>
          </div>

          {/* Delivery Info */}
          <div className="delivery-section">
            <h3>Delivery</h3>
            <div className="delivery-input">
              <input type="text" placeholder="Enter pincode" />
              <button>Check</button>
            </div>
            <div className="delivery-info">
              <i className="bi bi-truck"></i>
              <span>Free delivery on orders above ₹500</span>
            </div>
          </div>

          {/* Product Highlights */}
          <div className="highlights-section">
            <h3>Product Highlights</h3>
            <ul>
              <li>Category: {product.category}</li>
              <li>Brand: {product.brand}</li>
              <li>100% Original Products</li>
              <li>Easy 7 days return policy</li>
              {product.releaseDate && (
                <li>Release Date: {new Date(product.releaseDate).toLocaleDateString()}</li>
              )}
            </ul>
          </div>
        </div>
      </div>

      {/* Description & Specifications */}
      <div className="product-details-tabs">
        <div className="tabs-container">
          <div className="tab active">Description</div>
          <div className="tab">Specifications</div>
          <div className="tab">Reviews</div>
        </div>

        <div className="tab-content">
          {/* Description */}
          <div className="description-content">
            <h2>Product Description</h2>
            <p>{product.description || "Experience premium quality with this exceptional product. Designed with precision and care, it offers unmatched performance and durability. Perfect for your daily needs."}</p>
            
            <h3>Key Features:</h3>
            <ul>
              <li>High-quality materials ensuring long-lasting durability</li>
              <li>Sleek and modern design that fits any style</li>
              <li>Easy to use with user-friendly interface</li>
              <li>Backed by manufacturer warranty</li>
              <li>Eco-friendly and sustainable production</li>
            </ul>
          </div>

          {/* Specifications */}
          <div className="specifications-content">
            <h2>Specifications</h2>
            <table className="specs-table">
              <tbody>
                <tr>
                  <td className="spec-label">Product ID</td>
                  <td className="spec-value">{product.prodId}</td>
                </tr>
                <tr>
                  <td className="spec-label">Product Name</td>
                  <td className="spec-value">{product.prodName}</td>
                </tr>
                <tr>
                  <td className="spec-label">Brand</td>
                  <td className="spec-value">{product.brand}</td>
                </tr>
                <tr>
                  <td className="spec-label">Category</td>
                  <td className="spec-value">{product.category}</td>
                </tr>
                <tr>
                  <td className="spec-label">Price</td>
                  <td className="spec-value">₹{product.prodPrice}</td>
                </tr>
                {product.releaseDate && (
                  <tr>
                    <td className="spec-label">Release Date</td>
                    <td className="spec-value">{new Date(product.releaseDate).toLocaleDateString()}</td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductDetails;