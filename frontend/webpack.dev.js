const path = require('path');
const common = require('./webpack.common');
const { merge } = require('webpack-merge');
const Dotenv = require('dotenv-webpack');

module.exports = merge(common, {
  mode: 'development',
  output: {
    publicPath: '/',
    filename: 'js/bundle.js',
    path: path.join(__dirname, '/dist'),
    clean: true,
  },
  devtool: 'source-map',
  devServer: {
    port: 3000,
    open: true,
    hot: true,
    historyApiFallback: true,
  },
  plugins: [
    new Dotenv({
      path: path.resolve('./frontend-security/.env.development'),
    }),
  ],
});
