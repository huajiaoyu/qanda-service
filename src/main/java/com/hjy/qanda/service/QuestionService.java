package com.hjy.qanda.service;

import com.hjy.qanda.model.CheckNextQuestionRes;
import com.hjy.qanda.model.ProcessResp;
import com.hjy.qanda.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private SlotPointerHolder slotPointerHolder;

    public Question getNext(String slotId) {
        return slotPointerHolder.getSlotNext(Integer.parseInt(slotId));
    }

    public Question getCur(String slotId) {
        return slotPointerHolder.getSlotCur(Integer.parseInt(slotId));
    }

    public void markQuestion(String slotId, String qId) {
        slotPointerHolder.mark(Integer.parseInt(slotId), qId);
    }

    public CheckNextQuestionRes check(String slotId) {
        return slotPointerHolder.check(Integer.parseInt(slotId));
    }

    public void refreshSlot(String slotId) {
        slotPointerHolder.refresh(Integer.parseInt(slotId));
    }

    public ProcessResp getProc(String slotId) {
        return slotPointerHolder.getProc(Integer.parseInt(slotId));
    }

    public String getMarksMd(String slotId) {
        int sId = Integer.parseInt(slotId);
        List<Question> questions = slotPointerHolder.getQuestions(sId);
        // marks
        List<String> marks = slotPointerHolder.getMarks(sId);
        // lefts
        SlotPointerHolder.SlotPointer sp = slotPointerHolder.getSlotPointer(sId);
        List<String> curList = sp.getCurList();
        List<String> curs = curList.subList(sp.getCur(), curList.size());
        curs.addAll(marks);
        List<String> qs = curs.stream().map(qId -> {
            Question q = questions.get(Integer.parseInt(qId) - 1);
            String[] ss = q.getQuestion().split("-");
            return String.format("# %s\n## %s\n%s", ss[0], ss[1], q.getAnswer());
        }).toList();
        String res = String.join("\n", qs);
        return res;
    }
}
