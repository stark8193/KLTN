FROM node:20.17.0

RUN mkdir /app
COPY package*.json /app
WORKDIR /app
RUN npm install

COPY . .

CMD ["npm", "run", "dev"]