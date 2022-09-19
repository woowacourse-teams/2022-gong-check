const path = require('path');
const common = require('./webpack.common');
const { merge } = require('webpack-merge');
const Dotenv = require('dotenv-webpack');
const CompressionPlugin = require('compression-webpack-plugin');

module.exports = merge(common, {
  mode: 'production',
  output: {
    filename: 'js/[name].[chunkhash].js',
    clean: true,
  },
  devtool: false,
  plugins: [
    new Dotenv({
      path: path.resolve(
        process.env.NODE_ENV === 'production'
          ? './frontend-security/.env.production'
          : './frontend-security/.env.staging'
      ),
    }),
    new CompressionPlugin({
      algorithm: 'gzip',
      test: /\.js$|\.css$/,
      threshold: 10240,
      minRatio: 0.8,
    }),
  ],
});
