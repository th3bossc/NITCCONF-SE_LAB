import type { Metadata } from "next";
import { inter } from "@/fonts";
import "./global.scss";
import { AuthContextProvider } from "@/AuthContext";


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
      <body className={inter.className}>
        <AuthContextProvider>
          {children}
        </AuthContextProvider>
      </body>
    </html>
  );
}
