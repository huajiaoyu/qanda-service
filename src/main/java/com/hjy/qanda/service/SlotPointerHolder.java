package com.hjy.qanda.service;

import com.hjy.qanda.model.CheckNextQuestionRes;
import com.hjy.qanda.model.ProcessResp;
import com.hjy.qanda.model.Question;
import com.hjy.qanda.utils.FileUtil;
import com.hjy.qanda.utils.MarkdownUtils;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SlotPointerHolder {

    @Autowired
    private FileService fileService;


    @Value("${qanda.questions.shuffle:true}")
    public boolean isShuffled;

    public static final Integer SLOTS_NUM = 6;

    public static final String PTR_PATH_PREFIX = "ptr";
    public static final String QUESTIONS_PATH = "QA1.md";
//    public static final String QUESTIONS_PATH = "test.md";

    public void refresh(int slotId) {
        // 1. 新顺序
        List<String> cur = new ArrayList<>();
        for (int i = 1; i <= questions.get(slotId).size(); i++) {
            cur.add(String.valueOf(i));
        }
        if (isShuffled) {
            Collections.shuffle(cur);
        }
        // 2. 重置slot
        SlotPointer sp = new SlotPointer();
        sp.setCurList(cur);
        slotPointers.set(slotId, sp);
        // 3. 清空marks
        marks.set(slotId, new ArrayList<>());
    }

    public ProcessResp getProc(int slotId) {
        ProcessResp res = new ProcessResp();
        res.setCur(slotPointers.get(slotId).getCur() + 1);
        res.setTotal(slotPointers.get(slotId).getCurList().size());
        return res;
    }

    @Data
    static class SlotPointer {

        private int cur;

        private List<String> curList = new ArrayList<>();

        public String get() {
            return curList.get(cur);
        }

        public String getAndIncrement() {
            if (cur >= curList.size()) {
                return "";
            }
            String t = curList.get(cur);
            cur++;
            return t;
        }


    }


    private static List<SlotPointer> slotPointers = new ArrayList<>(SLOTS_NUM);

    private static List<List<String>> marks = new ArrayList<>(SLOTS_NUM);

    private static List<List<Question>> questions = new ArrayList<>(SLOTS_NUM);

    // 初始化指针

    static {
        for (int i = 0; i < SLOTS_NUM; i++) {
            SlotPointer sp = new SlotPointer();
            slotPointers.add(sp);
            String t = FileUtil.readFileStr(PTR_PATH_PREFIX + i);
            if (Strings.isNotBlank(t)) {
                String[] ss = t.split("\\|");
                sp.setCur(Integer.parseInt(ss[0]));
                sp.setCurList(List.of(ss[1].split(",")));
            }
        }
    }
    // 初始化标记

    static {
        for (int i = 0; i < SLOTS_NUM; i++) {
            marks.add(new ArrayList<>());
            String t = FileUtil.readFileStr("mark" + i);
            if (Strings.isNotBlank(t)) {
                String[] ss = t.split(",");
                if (ss.length != 0) {
                    marks.set(i, List.of(ss));
                }
            }
        }
    }

    static {
        for (int i = 0; i < SLOTS_NUM; i++) {
            questions.add(new ArrayList<>());
        }
    }

    public void setQuestions(int slotId, String s) {
        questions.set(slotId, MarkdownUtils.getQuestionsFromMarkdown(s));
    }

    public Question getSlotNext(int slotId) {
        String qId = slotPointers.get(slotId).getAndIncrement();
        return null;
    }

    public Question getSlotCur(int slotId) {
        String qId = slotPointers.get(slotId).get();
        return questions.get(slotId).get(Integer.parseInt(qId) - 1);
    }

    public SlotPointer getSlotPointer(int slotId) {
        return slotPointers.get(slotId);
    }

    public List<String> getMarks(int slotId) {
        return marks.get(slotId);
    }

    public List<Question> getQuestions(int slotId) {
        return questions.get(slotId);
    }

    public void mark(int slotId, String qId) {
        marks.get(slotId).add(qId);
    }

    public CheckNextQuestionRes check(int slotId) {
        SlotPointer sp = slotPointers.get(slotId);
        int cur = sp.getCur();
        // 1. 当前存在
        if (!sp.curList.isEmpty() && cur < sp.curList.size()) {
            return new CheckNextQuestionRes("有剩余问题", true);
        }
        // 2. 当前不存在
        // 2.1 marks也不存在
        List<String> mks = marks.get(slotId);
        if (mks.isEmpty()) {
            return new CheckNextQuestionRes("已全部完成", false);
        }
        // 2.2 marks存在, marks替换
        slotPointers.get(slotId).setCur(0);
        // mks随机化
        if (isShuffled) {
            Collections.shuffle(mks);
        }
        slotPointers.get(slotId).setCurList(mks);
        // 清空marks
        marks.set(slotId, new ArrayList<>());
        return new CheckNextQuestionRes("使用标记替换", true);
    }
}
