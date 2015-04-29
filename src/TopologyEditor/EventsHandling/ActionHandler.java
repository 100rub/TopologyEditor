package TopologyEditor.EventsHandling;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by VEF on 23.04.2015.
 */
public final class ActionHandler
{
    private Queue<ActionInfo> _actions;
    private Queue<ActionInfo> _undoes;



    public ActionHandler()
    {
        _actions = new LinkedList<ActionInfo>();
        _undoes = new LinkedList<ActionInfo>();
    }



    public void Do(Action action, ActionParameters parameters)
    {
        _actions.add(action.Apply(parameters));
        _undoes.clear();
    }

    public boolean Undo()
    {
        if (_actions.isEmpty())
            return false;
        ActionInfo info = _actions.poll();
        _undoes.add(info.getOpposite().Apply(info.getParameters()));
        return true;
    }

    public boolean Redo()
    {
        if (_undoes.isEmpty())
            return false;
        ActionInfo info = _undoes.poll();
        _actions.add(info.getOpposite().Apply(info.getParameters()));
        return true;
    }

    public Iterable<ActionInfo> GetActions()
    {
        return _actions;
    }

    public ActionInfo GetLastAction()
    {
        return _actions.peek();
    }

    public ActionInfo GetLastUndo()
    {
        return _undoes.peek();
    }
}
