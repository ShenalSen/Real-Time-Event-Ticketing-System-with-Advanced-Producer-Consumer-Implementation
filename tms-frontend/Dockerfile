# Use an official Node.js image to build the React app
FROM node:18 AS build

# Set the working directory
WORKDIR /tms-frontend

# Copy package.json and install dependencies
COPY package.json package-lock.json ./
RUN npm install

# Copy the rest of the app's code
COPY . .

# Build the app
RUN npm run build

# Use a lightweight web server to serve the built app
FROM nginx:stable-alpine
COPY --from=build /tms-frontend/build /usr/share/nginx/html

# Expose the default port for Nginx
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
