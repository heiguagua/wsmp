//>>built
define("dijit/_Widget", "dojo/aspect dojo/_base/config dojo/_base/connect dojo/_base/declare dojo/has dojo/_base/kernel dojo/_base/lang dojo/query dojo/ready ./registry ./_WidgetBase ./_OnDijitClickMixin ./_FocusMixin dojo/uacss ./hccss".split(" "), function (d, k, e, l, m, f, n, p, q, r, s, t, u) {
    function b() {
    }

    function h(a) {
        return function (c, g, f, d) {
            return c && "string" == typeof g && c[g] == b ? c.on(g.substring(2).toLowerCase(), n.hitch(f, d)) : a.apply(e, arguments)
        }
    }

    d.around(e, "connect", h);
    f.connect && d.around(f, "connect",
        h);
    d = l("dijit._Widget", [s, t, u], {
        onClick: b,
        onDblClick: b,
        onKeyDown: b,
        onKeyPress: b,
        onKeyUp: b,
        onMouseDown: b,
        onMouseMove: b,
        onMouseOut: b,
        onMouseOver: b,
        onMouseLeave: b,
        onMouseEnter: b,
        onMouseUp: b,
        constructor: function (a) {
            this._toConnect = {};
            for (var c in a)this[c] === b && (this._toConnect[c.replace(/^on/, "").toLowerCase()] = a[c], delete a[c])
        },
        postCreate: function () {
            this.inherited(arguments);
            for (var a in this._toConnect)this.on(a, this._toConnect[a]);
            delete this._toConnect
        },
        on: function (a, c) {
            return this[this._onMap(a)] ===
            b ? e.connect(this.domNode, a.toLowerCase(), this, c) : this.inherited(arguments)
        },
        _setFocusedAttr: function (a) {
            this._focused = a;
            this._set("focused", a)
        },
        setAttribute: function (a, b) {
            f.deprecated(this.declaredClass + "::setAttribute(attr, value) is deprecated. Use set() instead.", "", "2.0");
            this.set(a, b)
        },
        attr: function (a, b) {
            if (k.isDebug) {
                var d = arguments.callee._ach || (arguments.callee._ach = {}), e = (arguments.callee.caller || "unknown caller").toString();
                d[e] || (f.deprecated(this.declaredClass + "::attr() is deprecated. Use get() or set() instead, called from " +
                    e, "", "2.0"), d[e] = !0)
            }
            return 2 <= arguments.length || "object" === typeof a ? this.set.apply(this, arguments) : this.get(a)
        },
        getDescendants: function () {
            f.deprecated(this.declaredClass + "::getDescendants() is deprecated. Use getChildren() instead.", "", "2.0");
            return this.containerNode ? p("[widgetId]", this.containerNode).map(r.byNode) : []
        },
        _onShow: function () {
            this.onShow()
        },
        onShow: function () {
        },
        onHide: function () {
        },
        onClose: function () {
            return !0
        }
    });
    m("dijit-legacy-requires") && q(0, function () {
        require(["dijit/_base"])
    });
    return d
});