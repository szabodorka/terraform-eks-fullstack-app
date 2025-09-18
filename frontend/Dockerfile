FROM node:alpine AS build
WORKDIR /app
COPY package*.json /app/
RUN npm i
COPY . .
RUN npm run build

FROM nginx:alpine
COPY ./nginx.conf /etc/nginx/
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
