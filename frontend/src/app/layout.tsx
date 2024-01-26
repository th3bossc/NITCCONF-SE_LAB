import type { Metadata } from "next";
import { inter } from "@/fonts";
import "./globals.css";



export const metadata: Metadata = {
  title: "NITCONF",
  description: "NITCONF - SE LAB PROJECT",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" style={{scrollBehavior: "smooth"}}>
      <body className={inter.className}>{children}</body>
    </html>
  );
}
