const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const { CleanWebpackPlugin } = require('clean-webpack-plugin');
const Dotenv = require('dotenv-webpack');

const config = {
  entry: './src/index.tsx',
  output: {
    publicPath: '/',
    path: path.resolve(__dirname, 'dist'),
    filename: 'bundle.[chunkhash].js',
  },
  devServer: {
    port: 3000,
    open: true,
    hot: true,
    historyApiFallback: true,
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: 'public/index.html',
      favicon: 'src/assets/favicon.png',
    }),
    new CleanWebpackPlugin({
      cleanAfterEveryBuildPatterns: ['dist'],
    }),
    new Dotenv({
      path: path.resolve(
        process.env.NODE_ENV === 'production'
          ? `./frontend-security/.env.production`
          : process.env.NODE_ENV === 'staging'
          ? `./frontend-security/.env.staging`
          : `./frontend-security/.env.development`
      ),
    }),
  ],
  module: {
    rules: [
      {
        test: /\.(ts|tsx)?$/,
        exclude: ['/node_modules/'],
        use: {
          loader: 'babel-loader',
        },
      },
      {
        test: /\.(png|jpe?g|gif|svg|webp)$/,
        use: {
          loader: 'file-loader',
          options: {
            name: '[path]/[name].[ext]',
          },
        },
      },
    ],
  },
  resolve: {
    extensions: ['.tsx', '.ts', '.jsx', '.js', '...'],
  },
};

module.exports = env => {
  switch (process.env.NODE_ENV) {
    case 'production':
      config.mode = 'production';
      break;
    case 'staging':
      config.mode = 'production';
      break;
    default:
      config.mode = 'development';
      break;
  }
  return config;
};
