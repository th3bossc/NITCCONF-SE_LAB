import { Review, Paper, } from "@/types";
import { oswald } from "@/fonts";
import PdfLink from "../PdfLink";
import Image from "next/image";
import editIcon from '/public/edit.svg';
import Comment from "../Comment";
import Tags from "../Tags";
import { AnimatePresence, motion } from "framer-motion";
import ReactLoading from 'react-loading';
import Link from "next/link";
const PaperPage = ({
    paper,
}: {
    paper: Paper | null
}) => {
    let comments: Review[] = [];
    if (paper) {
        const temp = paper.documentVersions.map((doc, index) => {
            return (
                doc.reviews.map((review, index) => {
                    return review
                })
            );
        })
        comments = temp.flat(1);
    }
    return (
        <div className="w-full h-screen xl:flex xl:justify-center xl:pt-16 text-[#111]">
            <div className="w-full h-full xl:w-[80%] xl:rounded-t-[40px] pt-32 p-4 xl:p-10 2xl:p-16 bg-white relative overflow-y-scroll">
                <AnimatePresence>
                    {
                        paper ? (
                            <motion.div>
                                <div className={`${oswald.className} font-bold text-4xl 2xl:text-6xl uppercase text-center xl:text-left w-full flex justify-center xl:justify-start items-center gap-2`}>
                                    {paper.title}
                                    <Link
                                        href={`/dashboard/${paper.id}/edit`}
                                    >
                                        <motion.div
                                            whileHover={{ rotate: 5 }}
                                            whileTap={{ scale: 0.95 }}
                                        >
                                            <Image src={editIcon} alt="edit-icon" />
                                        </motion.div>
                                    </Link>
                                </div>
                                <div className="mt-4 flex gap-4 justify-center xl:justify-start">
                                    {
                                        paper.tags.map((tag) => (
                                            <Tags key={tag.id} {...tag} />
                                        ))
                                    }
                                </div>
                                <div className="mt-16 2xl:mt-32 w-full xl:w-[30rem] 2xl:w-[50rem] font-medium text-md 2xl:text-2xl text-center xl:text-left">
                                    {paper.description}
                                </div>
                                <div className="flex flex-col mt-4 2xl:mt-8 gap-2 font-regular text-md 2xl:text-2xl text-center xl:text-left">
                                    <span> language: {paper.language} </span>
                                    <span> level: {paper.level} </span>
                                </div>
                                {
                                    paper.id && (
                                        <PdfLink id={paper.id} status={paper.status} />
                                    )
                                }

                                <div className="w-full mt-16 2xl:mt-32">
                                    <h2 className={`w-full text-center text-3xl 2xl:text-5xl font-bold ${oswald.className}`} > COMMENTS </h2>
                                    <div className="flex flex-col gap-4 mt-8">
                                        {
                                            (comments.length === 0) ? (
                                                <div className="w-full h-full p-4 flex items-center justify-center">
                                                    <div className="w-full md:w-1/2 p-4  flex items-center justify-center border border-black border-dashed border-2">
                                                        No comments yet...
                                                    </div>
                                                </div>
                                            ) : (
                                                comments.map((review) => (
                                                    <Comment key={review.id} {...review} />
                                                )))
                                        }
                                    </div>
                                </div>
                            </motion.div>
                        ) : (
                            <motion.div className="absolute w-full h-full top-0 left-0 flex items-center justify-center">
                                <ReactLoading
                                    type="bars"
                                    color="#000"
                                    height="4rem"
                                    width="4rem"
                                />
                            </motion.div>
                        )
                    }
                </AnimatePresence>
            </div>
        </div>
    )
}

export default PaperPage;