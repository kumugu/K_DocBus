module.exports = {
    extends: ["eslint:recommended", "plugin:react/recommended", "plugin:@typescript-eslint/recommended", "prettier"],
    plugins: ["react", "@typescript-eslint"],
    parser: "@typescript-eslint/parser",
    rules: {
      "prettier/prettier": "error",
    },
  };
  