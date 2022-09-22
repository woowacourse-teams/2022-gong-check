const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
  entry: './src/index.tsx',
  resolve: { extensions: ['.tsx', '.ts', '.jsx', '.js', '...'] },
  plugins: [
    new HtmlWebpackPlugin({
      template: 'public/index.html',
      favicon: 'src/assets/favicon.png',
    }),
  ],
  module: {
    rules: [
      {
        test: /\.(ts|tsx|js|jsx)?$/,
        exclude: ['/node_modules/'],
        use: {
          loader: 'babel-loader',
        },
      },
      {
        test: /\.(png|jpe?g|gif|svg|webp)$/,
        exclude: ['/node_modules/'],
        use: {
          loader: 'file-loader',
          options: {
            name: 'assets/images/[name].[ext]',
          },
        },
      },
      {
        test: /\.(eot|ttf|woff|woff2)$/i,
        loader: 'url-loader',
        options: {
          name: 'assets/fonts/[name].[ext]',
        },
      },
    ],
  },
};
