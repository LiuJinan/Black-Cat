package cn.liujinnan.tools.utils.markdown;

import com.vladsch.flexmark.ast.FencedCodeBlock;
import com.vladsch.flexmark.html.AttributeProvider;
import com.vladsch.flexmark.html.AttributeProviderFactory;
import com.vladsch.flexmark.html.IndependentAttributeProviderFactory;
import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.html.MutableAttributes;

/**
 * @author ljn
 * @version 1.0
 * @date 2024-04-23 16:23
 */
class CodePreLineNumbersAttributeProvider implements AttributeProvider {
    @Override
    public void setAttributes(Node node, AttributablePart part, MutableAttributes attributes) {
        // 定位到<pre>标签元素进行修改
        if (node instanceof FencedCodeBlock && part == AttributablePart.NODE) {
            attributes.addValue("class", "line-numbers");
        }
    }

    static AttributeProviderFactory Factory() {
        return new IndependentAttributeProviderFactory() {
            @Override
            public AttributeProvider apply(LinkResolverContext context) {
                return new CodePreLineNumbersAttributeProvider();
            }
        };
    }
}

