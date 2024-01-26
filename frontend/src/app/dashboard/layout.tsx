import Sidebar from "@/components/Sidebar";

export default function RootLayout({
    children,
  }: Readonly<{
    children: React.ReactNode;
  }>) {
    return (
        <div className="h-screen w-full bg-[#050729] text-white">
            <div className="hidden xl:block">
                <Sidebar/>
            </div>
            {children}
        </div>
    )
}