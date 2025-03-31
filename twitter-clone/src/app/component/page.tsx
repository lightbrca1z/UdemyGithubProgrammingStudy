"use client";  // ← これを一行目に追加

import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";

export default function TweetForm() {
  const [text, setText] = useState("");
  const [image, setImage] = useState<File | null>(null);

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files[0]) {
      setImage(e.target.files[0]);
    }
  };

  const handleSubmit = () => {
    alert(`投稿: ${text}${image ? ` / 画像: ${image.name}` : ""}`);
    setText("");
    setImage(null);
  };

  return (
    <Card className="w-full max-w-2xl mx-auto p-4 mb-4">
      <CardContent>
        <div className="flex space-x-4 items-start">
          <div className="w-10 h-10 bg-gray-300 rounded-full" />

          <div className="flex-1">
            <h1>Twttier投稿画面</h1>
            <textarea
              value={text}
              onChange={(e) => setText(e.target.value)}
              placeholder="今どうしてる？"
              className="w-full border rounded p-2 resize-none focus:outline-none focus:ring focus:ring-blue-300"
              rows={3}
            />

            <div className="mt-2 flex justify-between items-center">
              <input type="file" accept="image/*" onChange={handleImageChange} />
              <Button onClick={handleSubmit} disabled={!text && !image}>
                投稿
              </Button>
            </div>

            {image && (
              <div className="mt-2">
                <img
                  src={URL.createObjectURL(image)}
                  alt="preview"
                  className="w-32 h-32 object-cover rounded"
                />
              </div>
            )}
          </div>
        </div>
      </CardContent>
    </Card>
  );
}
