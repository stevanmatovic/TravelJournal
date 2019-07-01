package com.example.traveljournal.utils;

import com.example.traveljournal.entity.CheckListWithItems;
import com.example.traveljournal.entity.Component;
import com.example.traveljournal.entity.Text;
import com.example.traveljournal.entity.Visual;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

public class DataSourceMerger {

    public static LiveData<List<Component>> mergeDataSources(LiveData texts, LiveData visuals, LiveData checklists) {
        final MediatorLiveData<List<Component>> mergedSources = new MediatorLiveData<>();
        mergedSources.setValue(new ArrayList<Component>());
        mergedSources.getValue();
        addComponentSource(mergedSources,texts,Component.TEXT);
        addComponentSource(mergedSources,visuals,Component.VISUAL);
        addComponentSource(mergedSources,checklists,Component.CHECKLIST);

        return mergedSources;
    }

    private static void addComponentSource(final MediatorLiveData<List<Component>> mergedSources, LiveData source, final int componentType){
        mergedSources.addSource(source, new Observer() {
            @Override
            public void onChanged(Object value) {
                List<Component> current = mergedSources.getValue();
                if (current == null)
                    mergedSources.setValue((List<Component>) value);
                else {
                    int type = componentType;
                    Iterator<Component> iter = current.iterator();
                    while (iter.hasNext()) {
                        Component component = iter.next();
                        if (component.getType() == type)
                            iter.remove();
                    }
                    current.addAll((List<Component>) value);
                    mergedSources.setValue(current);

                }
            }
        });
    }

}
