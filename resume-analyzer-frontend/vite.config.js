import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// Requests to /resume/... are forwarded to the Spring Boot backend,
// so no CORS setup is needed while developing.
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    proxy: {
      "/resume": "http://localhost:8080",
    },
  },
});
