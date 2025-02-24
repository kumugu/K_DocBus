/** @type {import('eslint').Linter.Config} */
module.exports = {
    env: {
      browser: true,
      es2021: true,
    },
    extends: [
      "eslint:recommended",
      "plugin:@typescript-eslint/recommended",
      "plugin:prettier/recommended", // Prettier와 ESLint 충돌 방지
    ],
    parser: "@typescript-eslint/parser",
    parserOptions: {
      ecmaVersion: "latest",
      sourceType: "module",
    },
    plugins: ["@typescript-eslint", "prettier"],
    rules: {
      "prettier/prettier": "error", // Prettier 포맷팅을 ESLint 에러로 처리
      "@typescript-eslint/no-unused-vars": "warn", // 사용하지 않는 변수 경고
      "no-console": "warn", // console.log 사용 시 경고
    },
  };
  