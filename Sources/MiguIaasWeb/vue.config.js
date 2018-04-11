const path = require('path');

function resolve(dir) {
  return path.join(__dirname, dir);
}

module.exports = {
  baseUrl: '/', // default base url
  configureWebpack: {
    resolve: {
      extensions: ['.js', '.vue', '.json'],
      alias: {
        vue$: 'vue/dist/vue.esm.js',
        '@': resolve('src'),
      },
    },
    module: {
      rules: [
        {
          test: /\.svg$/,
          loader: 'svg-sprite-loader',
          include: [resolve('src/icons')],
          options: {
            symbolId: 'icon-[name]',
          },
        },
      ],
    },
  },
  chainWebpack: (config) => {
    // remove the old loader
    config.module.rules.delete('svg');
  },
  lintOnSave: true,
};
