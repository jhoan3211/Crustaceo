-- V1_0_0__initschema.sql

-- =====================
-- Tabla: users
-- =====================
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    active BOOLEAN NOT NULL DEFAULT true
);

CREATE INDEX idx_users_email ON users(email);


-- =====================
-- Tabla: product
-- =====================
CREATE TABLE product (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    category VARCHAR(15) NOT NULL,
    description TEXT NOT NULL,
    price DECIMAL(19,2) NOT NULL,
    image VARCHAR(20) NOT NULL
);

CREATE INDEX idx_product_name ON product(name);

-- =====================
-- Tabla: orders
-- =====================
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    address VARCHAR(255) NOT NULL,
    amount DECIMAL(19,2),
    status VARCHAR(20),
    user_id BIGINT NOT NULL REFERENCES users(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_orders_customer_id ON orders(user_id);

-- =====================
-- Tabla: order_details
-- =====================
CREATE TABLE order_detail (
    id VARCHAR(255) PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES orders(id),
    product_id BIGINT NOT NULL REFERENCES product(id),
    quantity INT NOT NULL,
    subtotal DECIMAL(19,2)
);

CREATE INDEX idx_order_detail_product_id ON order_detail(product_id);
CREATE INDEX idx_order_detail_order_id ON order_detail(order_id);



