#!/bin/bash

# Stop script if any command fails
set -e

# Step 1: Start cloudflared tunnel (random URL) และบันทึก log
cloudflared tunnel --url http://localhost:9000 --logfile tunnel.log --loglevel info &
TUNNEL_PID=$!

echo "Waiting for tunnel to start..."
sleep 5

# Step 2: Read tunnel.log และดึง URL
PUBLIC_URL=$(grep -o "https://[a-zA-Z0-9.-]*\.trycloudflare\.com" tunnel.log | head -n 1)

if [ -z "$PUBLIC_URL" ]; then
  echo "❌ Failed to detect public URL. Exiting..."
  kill $TUNNEL_PID
  exit 1
fi

# Step 3: Export as environment variable (สำหรับ process อื่น)
export PUBLIC_TUNNEL_URL="$PUBLIC_URL"

# Also write to .env file (ให้ process อื่นๆ เช่น FastAPI, Node.js ใช้ได้)
# echo "PUBLIC_TUNNEL_URL=$PUBLIC_TUNNEL_URL" > .env
# setx VITE_SERVER_ENDPOINT $PUBLIC_URL


# Step 4: แสดง URL
echo
echo "================================"
echo "Your public URL is: $PUBLIC_TUNNEL_URL"
echo "Exported to .env file"
echo "================================"

# Step 5: Start Your App (Example: FastAPI app)
# (ถ้าใช้ uvicorn)
# PUBLIC_TUNNEL_URL=$PUBLIC_TUNNEL_URL uvicorn main:app --reload

VITE_SERVER_ENDPOINT=$PUBLIC_URL npm run start:dev && cloudflared tunnel --url http://localhost:5173

# Step 6: Wait for user input to stop tunnel
read -p "Press ENTER to stop tunnel..."

# Step 7: Stop cloudflared tunnel
kill $TUNNEL_PID
echo "Tunnel stopped."
