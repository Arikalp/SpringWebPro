# Quick Start Guide - Authentication & Authorization

## 🚀 What Was Fixed

Your authentication system had **multiple critical issues** that have now been resolved:

### Backend (Spring Boot) - 9 Files Fixed/Created
- ✅ SecurityConfig.java - Complete rewrite with JWT support
- ✅ JwtFilter.java - **NEW** - Intercepts and validates JWT tokens
- ✅ UserController.java - **NEW** - Login & Register endpoints
- ✅ JWTService.java - Complete rewrite with proper JWT implementation
- ✅ UserService.java - Fixed authentication flow
- ✅ MyUserDetailsService.java - Fixed database user loading
- ✅ Users.java - Added JPA annotations and methods
- ✅ UserRepo.java - Added JPA repository interface
- ✅ UserPrincipal.java - Fixed all return values

### Frontend (React) - 6 Files Fixed/Created
- ✅ Login.jsx - **NEW** - Beautiful login page
- ✅ Login.css - **NEW** - Styled login form
- ✅ Signup.jsx - **NEW** - Registration page
- ✅ Signup.css - **NEW** - Styled signup form
- ✅ App.jsx - Added auth routes
- ✅ axios.jsx - Added JWT interceptors
- ✅ Navbar.jsx - Added login/logout UI

---

## 🎯 How to Test (5 Minutes)

### Step 1: Start Backend
```bash
cd server
mvn spring-boot:run
```
Wait for: `Started SpringWebProApplication...`

### Step 2: Start Frontend
```bash
cd client
npm run dev
```
Visit: `http://localhost:5173`

### Step 3: Test Authentication

#### Create an Account
1. Click **"Sign Up"** in the navbar (or go to `/signup`)
2. Enter username: `testuser`
3. Enter password: `password123`
4. Confirm password: `password123`
5. Click **"Sign Up"**
6. ✅ Success! You'll be redirected to login

#### Login
1. Enter username: `testuser`
2. Enter password: `password123`
3. Click **"Login"**
4. ✅ You're now logged in! See "Hi, testuser" in navbar

#### Test Protected Routes
1. Try adding a product
2. Try viewing products
3. ✅ All requests now include your JWT token automatically!

#### Logout
1. Click **"Logout"** button
2. ✅ Token is cleared, redirected to login

---

## 🔐 How It Works

### Authentication Flow

```
┌─────────────┐
│   Signup    │
│   Form      │
└──────┬──────┘
       │
       ▼
┌─────────────────────┐
│ POST /api/register  │  → Password encrypted with BCrypt
│ {username,password} │  → User saved to database
└──────┬──────────────┘
       │
       ▼
┌─────────────┐
│ Login Form  │
└──────┬──────┘
       │
       ▼
┌──────────────────┐
│ POST /api/login  │  → Credentials validated
│ {username,pass}  │  → JWT token generated
└──────┬───────────┘
       │
       ▼
┌──────────────────┐
│  Store Token in  │
│   localStorage   │
└──────┬───────────┘
       │
       ▼
┌────────────────────────┐
│  All Future Requests   │
│ Include JWT in Header: │
│ Authorization: Bearer  │
│ eyJhbGc...            │
└────────────────────────┘
```

### JWT Token Structure

```json
{
  "header": {
    "alg": "HS256",
    "typ": "JWT"
  },
  "payload": {
    "sub": "testuser",
    "iat": 1234567890,
    "exp": 1234603890
  },
  "signature": "..."
}
```

---

## 📝 API Endpoints

### Public (No Login Required)
```
POST /api/register
POST /api/login
```

### Protected (Requires JWT Token)
```
GET    /api/products
GET    /api/products/{id}
POST   /api/addProducts
POST   /api/updateProducts
DELETE /api/deleteProducts/{id}
GET    /api/products/search?keyword=xxx
```

---

## 🔍 Verify It's Working

### Check Token in Browser
1. Open DevTools (F12)
2. Go to **Application** tab
3. Click **Local Storage** → `http://localhost:5173`
4. You should see:
   - `token`: `eyJhbGciOiJIUzI1NiJ9...`
   - `username`: `testuser`

### Check Network Requests
1. Open DevTools (F12)
2. Go to **Network** tab
3. Make any request (e.g., view products)
4. Click on the request
5. Check **Request Headers**
6. You should see:
   ```
   Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
   ```

---

## ⚙️ Configuration

### Token Expiration
Default: **10 hours**

To change, edit [JWTService.java](server/src/main/java/com/SpringWebPro/SpringWebPro/service/JWTService.java):
```java
.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                                                    // 1000ms * 60s * 60m * 10h
```

### Secret Key
⚠️ **IMPORTANT**: Change for production!

Edit [JWTService.java](server/src/main/java/com/SpringWebPro/SpringWebPro/service/JWTService.java):
```java
private String secretKey = "YOUR-SECURE-SECRET-KEY-HERE";
```

### Database
Ensure `application.properties` is configured:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/yourdb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

---

## 🐛 Troubleshooting

### "401 Unauthorized" after login
- ✅ Check browser console for errors
- ✅ Verify token in localStorage
- ✅ Check database has the user

### "User 404" error
- ✅ Database might be empty
- ✅ Check database connection in `application.properties`
- ✅ Verify table "users" exists

### CORS errors
- ✅ Verify backend runs on port 8080
- ✅ Verify frontend runs on port 5173
- ✅ Check `@CrossOrigin` annotation exists

### Token not sent with requests
- ✅ Check [axios.jsx](client/src/axios.jsx) interceptor
- ✅ Clear browser cache and localStorage
- ✅ Check Network tab for Authorization header

---

## 🎨 UI Features

### Login Page
- Gradient background (purple theme)
- Form validation
- Error messages
- Link to signup

### Signup Page
- Gradient background (pink theme)
- Password confirmation
- Client-side validation
- Link to login

### Navbar Updates
- Shows "Hi, username" when logged in
- Logout button
- Login/Signup buttons when logged out
- Auto-updates on auth state change

---

## 📚 Files Changed

### Backend Files
```
server/src/main/java/com/SpringWebPro/SpringWebPro/
├── config/
│   ├── SecurityConfig.java      (FIXED)
│   └── JwtFilter.java           (NEW)
├── controllers/
│   └── UserController.java      (NEW)
├── models/
│   ├── Users.java               (FIXED)
│   └── UserPrincipal.java       (FIXED)
├── repository/
│   └── UserRepo.java            (FIXED)
└── service/
    ├── JWTService.java          (FIXED)
    ├── UserService.java         (FIXED)
    └── MyUserDetailsService.java(FIXED)
```

### Frontend Files
```
client/src/
├── components/
│   ├── Login.jsx        (NEW)
│   ├── Login.css        (NEW)
│   ├── Signup.jsx       (NEW)
│   ├── Signup.css       (NEW)
│   ├── Navbar.jsx       (UPDATED)
│   └── Navbar.css       (UPDATED)
├── App.jsx              (UPDATED)
└── axios.jsx            (UPDATED)
```

---

## ✅ All Issues Resolved

| Issue | Status |
|-------|--------|
| SecurityConfig typos | ✅ Fixed |
| Missing AuthenticationManager | ✅ Added |
| Missing JWT Filter | ✅ Created |
| Broken JWTService | ✅ Rewritten |
| Empty UserRepo | ✅ Implemented |
| Broken UserPrincipal | ✅ Fixed |
| Missing Login/Register endpoints | ✅ Created |
| No frontend auth pages | ✅ Created Login & Signup |
| No JWT interceptors | ✅ Added to axios |
| No auth UI in navbar | ✅ Added Login/Logout buttons |

**Your authentication system is now fully functional! 🎉**
