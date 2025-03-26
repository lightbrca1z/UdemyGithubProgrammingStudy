'use client';

import Image from 'next/image';

export default function RoutingFormPage() {
  return (
    <div className="min-h-screen bg-green-100 p-6 sm:p-12 font-sans">
      {/* ヘッダー */}
      <header className="w-full flex flex-col sm:flex-row justify-between items-center max-w-6xl mx-auto">
        <div className="text-3xl font-bold text-purple-600">IT就労 ビズウェル</div>
        <nav className="flex space-x-4 text-pink-700 text-sm sm:text-base mt-4 sm:mt-0">
          <a href="/">ホーム</a>
          <a href="/routing/tanto">担当者一覧</a>
          <a href="/routing/kankei">関係機関一覧</a>
          <a href="/routing/kubun">区分一覧</a>
          <a href="/routing/area">エリア一覧</a>
          <a href="/routing/logout">ログアウト</a>
          <a href="/routing/shinkitouroku">新規登録</a>
        </nav>
      </header>

      {/* 新規登録ボタンと検索 */}
      <div className="mt-6 flex items-center justify-start max-w-6xl mx-auto space-x-4">
        <button className="bg-yellow-200 text-gray-800 px-6 py-2 rounded-full shadow-md text-lg">
          新規登録
        </button>
        <input
          type="text"
          placeholder="検索"
          className="border rounded-full px-4 py-2 focus:outline-none"
        />
      </div>

      {/* メイン：フォームと画像登録 */}
      <main className="mt-10 flex flex-col lg:flex-row gap-8 max-w-6xl mx-auto">
        {/* 入力フォーム */}
        <div className="bg-purple-900 text-white p-6 rounded-xl w-full lg:w-2/3">
          <h2 className="bg-purple-400 text-center text-xl font-bold py-2 rounded-t-xl mb-4">入力フォーム</h2>
          <form className="space-y-4 text-sm">
            {[
              ['区分入力', true],
              ['関係機関名', true],
              ['担当者名', true],
              ['TEL', true],
              ['携帯', false],
              ['FAX', false],
              ['メール', true],
              ['エリア', true],
              ['住所', false],
              ['備考', false],
            ].map(([label, required], index) => (
              <div key={index} className="flex flex-col">
                <label className="text-white">
                  {label}
                  {required && (
                    <span className="text-red-500 text-xs ml-1">※は必須項目です</span>
                  )}
                </label>
                <input
                  type="text"
                  className="bg-white text-black p-2 rounded"
                  required={required}
                />
              </div>
            ))}
          </form>
        </div>

        {/* 画像登録エリア */}
        <div className="bg-purple-200 p-6 rounded-xl w-full lg:w-1/3 relative">
          <h2 className="bg-purple-400 text-center text-xl font-bold py-2 rounded-t-xl mb-4">画像登録</h2>
          <p className="text-center mb-4">名刺の画像から登録</p>
          <div className="border border-gray-400 bg-white text-center py-10 rounded">
            <span className="text-black">ファイルを選択</span>
          </div>

          {/* ロボット画像（右上） */}
          <div className="absolute -top-8 right-4">
            <Image
              src="/robot.png" // 公開ディレクトリに配置
              alt="ロボット"
              width={60}
              height={60}
            />
          </div>
        </div>
      </main>

      {/* 登録ボタン */}
      <div className="flex justify-center mt-10">
        <button className="bg-yellow-200 text-purple-700 text-xl px-10 py-3 rounded-full shadow-md hover:bg-yellow-300 transition">
          登録
        </button>
      </div>
    </div>
  );
}
