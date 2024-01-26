import { Session } from "@/types";
import { oswald } from "@/fonts";
import PdfLink from "../PdfLink";
const SessionPage = ({
    session,
}: {
    session: Session | null
}) => {
    return (
        <div className="w-full h-screen flex justify-center pt-16 text-[#111]">
                <div className="w-[80%] rounded-t-[40px] p-10 2xl:p-16 bg-white relative">
            {
                session ? (
                    <>
                        <span className={`${oswald.className} font-bold text-4xl 2xl:text-6xl uppercase`}>{session.title}</span>
                        <div className="mt-4 flex gap-4">
                            <span> tag  </span>
                            <span> tag  </span>
                            <span> tag  </span>
                        </div>
                        <div className="mt-16 2xl:mt-32 w-[30rem] 2xl:w-[45rem] font-medium text-md 2xl:text-3xl">
                            {session.description}
                        </div>
                        <div className="flex flex-col mt-4 2xl:mt-8 gap-2 font-regular text-sm 2xl:text-2xl">
                            <span> language: {session.language} </span>
                            <span> level: {session.level} </span>
                        </div>
                        <PdfLink id={session.id} status={session.status} />

                        <div className="w-full mt-16 2xl:mt-32">
                            <h2 className={`w-full text-center text-3xl 2xl:text-5xl font-bold ${oswald.className}`} > COMMENTS </h2>
                        </div>
                    </>
                ) : (
                    <div className="text-3xl">
                        Loading...
                    </div>
                )
            }
                </div>
        </div>
        
    )
}

export default SessionPage;